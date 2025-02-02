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
    db2redis_2(); // 
    /* redis 적재 시 처리 시간
     * 1) chunkSize:1_000_000L, Xmx:4g > 183.2110893 seconds
     * 2) chunkSize:2_000_000L, Xmx:4g > 1066.2696171 seconds
     * 3) chunkSize:1_000_000L, Xmx:4g > 24.0146274 seconds + 89.2843408 seconds  '변환/저장' 분리
     * 4) chunkSize:1_000_000L, Xmx:4g > 73.8473658 seconds '변환/저장' 통합
     * 5) chunkSize:1_000_000L, Xmx:3g > 75.207025 seconds '변환/저장' 통합
     * 6) chunkSize:1_000_000L, Xmx:2g > 77.7967522 seconds '변환/저장' 통합
     * 
     * 40,000,000 건일 경우 초당 14,000건 처리 시 2800 초(46분) 소요 예상
    */
    
    /* redis 적재 시 크기
     * 전체 필드 적재 시: 1건당 400 bytes (heap: 1gb)
     * 필수 필드 3개 적재 시: 1건당 130 bytes (heap: 0.5gb)
     * 
     * 전체 필드일 경우 40,000,000 건일 경우 redis 16gb 가 필요
     * 필수 필드일 경우 40,000,000 건               6gb 가 필요
    */
    
    /*redis 적재 시 처리 시간 테스트
     * chunk 2,000,000, Xmx4g, 필수 필드(3개) 일 경우 145.3474229 seconds
     * chunk 500,000, Xmx1g, 필수 필드(3개) 일 경우 40.4031113 seconds
     * chunk 250,000, Xmx500m, 필수 필드(3개) 일 경우 28.6671717 seconds (*)
    */
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
    Long chunkSize = 250_000L; // limits > Xmx1g:600_000L, Xmx2g:1_000_000L, Xmx4g: 2_000_000L
    System.out.println(String.format("  chunkSize: %d", chunkSize));
    
    log.info("[phase1] ir 전체 데이터 개수 조회");
    List<FundIrCountRec> allFunds = fundIrRepository.findFundIrCount();
    System.out.println(String.format("  allFunds: %s", allFunds));
    
    log.info("[phase2] 처리 대상 fundCd chunk 단위 분리");
    List<List<FundIrCountRec>> chunkList = this.splitList(allFunds, chunkSize);
    AtomicInteger chunkIdx = new AtomicInteger(-1);
    chunkList.forEach(subList -> {
      StopWatch stopWatch = new StopWatch("chunk-"+chunkIdx.incrementAndGet());
      stopWatch.start();
      log.info("[phase3] [{}] ir 데이터 조회", chunkIdx.get());
      List<String> fundCds = subList.stream().map(item -> item.fundCd()).collect(Collectors.toList());
      Long sum = subList.stream().mapToLong(item -> item.count()).sum();
      System.out.println(String.format("  sum(count): %d, fundCds: %s", sum, fundCds));
      Map<String, List<FundDto.FundIrRes>> groups = fundIrService.getAllFundIrs(fundCds);
      System.out.println(String.format("  groups.size: %d", groups.size()));
      
      log.info("[phase4] ir 데이터 변환 object -> json / redis 적재");
      final AtomicInteger logidx = new AtomicInteger(0);
      int groupCount = groups.size();
      groups.entrySet().stream().forEach(entry -> {
        String key = entry.getKey();
        List<FundDto.FundIrRes> fundIrs = entry.getValue();
        System.out.print(".");
        if (logidx.incrementAndGet() % (groupCount/5) == ((groupCount/5)-1)) System.out.println("");
        fundIrs.stream().parallel()
          .forEach(item -> {
            try {
              String redisKey = "fund:ir:"+key;
              String json = objectMapper.writeValueAsString(item);
              Double score = Double.parseDouble(item.getBasYmd());
              Set<String> existingValues = zSetOps.rangeByScore(redisKey, score, score);
              if (existingValues != null && !existingValues.isEmpty()) {
                existingValues.forEach(v -> zSetOps.remove(redisKey, v));
              }
              zSetOps.add(redisKey, json, score);
            } catch (JsonProcessingException e) {
              e.printStackTrace();
            }
          });
      });
      
      System.out.println("");
      
      stopWatch.stop();
      System.out.println(stopWatch.shortSummary() + "\n");
    });
  }
  
  
  @Deprecated
  private void db2redis_1() {
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
    });
  }
  
}
