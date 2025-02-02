package main.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import spring.custom.api.dto.FundDto;
import spring.custom.api.entity.rec.FundIrCountRec;
import spring.custom.api.entity.repository.FundIrRepository;
import spring.custom.api.entity.repository.FundMstRepository;
import spring.redis.service.FundIrService;

@SpringBootApplication
@ComponentScan(basePackages = {"spring"})
@EnableJpaRepositories(basePackages = {"spring"})
@EntityScan(basePackages = {"spring"})
@EnableRedisRepositories(basePackages = {"spring"})
@EnableCaching
@EnableTransactionManagement
@Slf4j
public class RedisSpringMain implements CommandLineRunner {
  
  public static void main(String[] args) {
    SpringApplication.run(RedisSpringMain.class, args);
  }
  
  @Autowired ObjectMapper objectMapper;
  @Autowired RedisTemplate<String, String> redisTemplate;
  @Autowired FundMstRepository fundMstRepository;
  @Autowired FundIrRepository fundIrRepository;
  @Resource(name="redisTemplate") ListOperations<String, String> listOps;
  @Autowired FundIrService fundIrService;
  ZSetOperations<String, String> zSetOps;
  
  @Override
  public void run(String... args) throws Exception {
    db2redis_2();
  }
  
  private List<List<FundIrCountRec>> splitList(List<FundIrCountRec> srcList, Long threshold) {
    List<List<FundIrCountRec>> result = new ArrayList<>();
    long sum = 0;
    int start = 0;
    int end = 0;
    for (int i=0; i<srcList.size(); i++) {
      sum += srcList.get(i).count();
      if (sum >= threshold) {
        end = i+1;
        result.add(srcList.subList(start, end));
        start = end; // next start
        sum = 0; // clear sum
      }
    }
    
    if (end < srcList.size()) {
      result.add(srcList.subList(start, srcList.size()));
    }
    
    return result;
  }
  
  
  record RedisSortedSet(String redisKey, String json, Double score) {}
  
  private void db2redis_2() {
    if (zSetOps == null) zSetOps = redisTemplate.opsForZSet();
    Long chunkSize = 1_000_000L; // limits > Xmx1g:600_000L, Xmx2g:1_000_000L, Xmx4g: 2_000_000L
    System.out.println(String.format("  chunkSize: %d", chunkSize));
    
    log.info("[phase1] ir 전체 데이터 개수 조회");
    List<FundIrCountRec> allFunds = fundIrRepository.findFundIrCount();
    System.out.println(String.format("  allFunds: %s", allFunds));
    
    log.info("[phase2] 처리 대상 fundCd chunk 단위 분리");
    List<List<FundIrCountRec>> result = this.splitList(allFunds, chunkSize);
    result.forEach(subList -> {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      log.info("[phase3] ir 데이터 조회");
      List<String> fundCds = subList.stream().map(item -> item.fundCd()).collect(Collectors.toList());
      Long sum = subList.stream().mapToLong(item -> item.count()).sum();
      System.out.println(String.format("  sum(count): %d, fundCds: %s", sum, fundCds));
      Map<String, List<FundDto.FundIrRes>> groups = fundIrService.getAllFundIrs(fundCds);
      System.out.println(String.format("  groups.size(): %d", groups.size()));
      
      log.info("[phase4] ir 데이터 변환 object -> json");
      final AtomicInteger logidx1 = new AtomicInteger(0);
      Map<String, List<RedisSortedSet>> newGroups = groups.entrySet().stream().parallel().map(entry -> {
        String key = entry.getKey();
        List<FundDto.FundIrRes> fundIrs = entry.getValue();
        System.out.print(String.format("  %s ", entry.getKey()));
        if (logidx1.incrementAndGet() % 7 == 6) System.out.println("");
        List<RedisSortedSet> value = fundIrs.stream().parallel()
            .map(item -> {
              try {
                String redisKey = "fund:ir:"+key;
                String json = objectMapper.writeValueAsString(item);
                Double score = Double.parseDouble(item.getBasYmd());
                return new RedisSortedSet(redisKey, json, score);
              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
              return null;
            })
            .filter(r -> r != null)
            .collect(Collectors.toList());
        return Map.entry(key, value);
      }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      
      System.out.println("");
      
      stopWatch.stop();
      System.out.println(stopWatch.shortSummary() + "\n");
      
      log.info("[phase5] ir 데이터 redis 저장");
      stopWatch.start();
      
      final AtomicInteger logidx2 = new AtomicInteger(0);
      newGroups.entrySet().stream().forEach(entry -> {
        System.out.print(String.format("  %s ", entry.getKey()));
        if (logidx2.incrementAndGet() % 7 == 6) System.out.println("");
        entry.getValue().stream().parallel().forEach(item -> {
          String key = item.redisKey;
          String value = item.json;
          Double score = item.score;
          Set<String> existingValues = zSetOps.rangeByScore(key, score, score);
          if (existingValues != null && !existingValues.isEmpty()) {
            existingValues.forEach(v -> zSetOps.remove(key, v));
          }
          zSetOps.add(key, value, score);
        });
      });
      
      System.out.println("");
      
      stopWatch.stop();
      System.out.println(stopWatch.shortSummary() + "\n");
      /*
       * 1) chunkSize:1_000_000L, Xmx:4gb > 183.2110893 seconds
       * 2) chunkSize:2_000_000L, Xmx:4gb > 1066.2696171 seconds
       * 3) chunkSize:1_000_000L, Xmx:4gb > 21.9754792 seconds + 83.6557743 seconds  '변환/저장' 분리
      */
    });
  }
}
