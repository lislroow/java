package main.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  
  private void db2redis_2() {
    if (zSetOps == null) zSetOps = redisTemplate.opsForZSet();
    Long chunkSize = 1_000_000L; // limits > Xmx1g:600_000L, Xmx2g:1_000_000L, Xmx4g: 2_000_000L
    System.out.println(String.format("  chunkSize: %d", chunkSize));
    
    log.info("[phase1] 펀드별 ir 개수 조회");
    List<FundIrCountRec> allFunds = fundIrRepository.findFundIrCount();
    System.out.println(String.format("  allFunds: %s", allFunds));
    
    log.info("[phase2] chunk 단위 펀드 목록 분리");
    List<List<FundIrCountRec>> result = this.splitList(allFunds, chunkSize);
    result.forEach(subList -> {
      List<String> fundCds = subList.stream().map(item -> item.fundCd()).collect(Collectors.toList());
      Long sum = subList.stream().mapToLong(item -> item.count()).sum();
      System.out.println(String.format("  sum(count): %d, fundCds: %s", sum, fundCds));
      
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      
      log.info("[phase3] ir 목록 조회");
      Map<String, List<FundDto.FundIrRes>> groups = fundIrService.getAllFundIrs(fundCds);
      System.out.println(String.format("  groups.size(): %d", groups.size()));
      log.info("[phase4] 펀드별 ir 목록 redis 저장");
      groups.entrySet().stream().forEach(entry -> {
        List<FundDto.FundIrRes> fundIrs = entry.getValue();
        System.out.print(String.format("  %s ", entry.getKey()));
        fundIrs.stream().parallel().forEach(item -> {
          try {
            String key = "fund:ir:"+entry.getKey();
            String value = objectMapper.writeValueAsString(entry.getValue());
            Double score = Double.parseDouble(item.getBasYmd());
            Set<String> existingValues = zSetOps.rangeByScore(key, score, score);
            if (existingValues != null && !existingValues.isEmpty()) {
              existingValues.forEach(v -> zSetOps.remove(key, v));
            }
            zSetOps.add(key, value, score);
          } catch (NumberFormatException|JsonProcessingException e) {
            e.printStackTrace();
          }
        });
      });
      System.out.println("");
      
      stopWatch.stop();
      System.out.println(stopWatch.shortSummary());
      /*
       * 1) chunkSize:1_000_000L, Xmx:4gb > 183.2110893 seconds
       * 2) chunkSize:2_000_000L, Xmx:4gb > 1066.2696171 seconds
      */
    });
  }
}
