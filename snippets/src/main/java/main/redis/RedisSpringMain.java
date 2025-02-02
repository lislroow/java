package main.redis;

import java.util.Comparator;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import spring.custom.api.dto.FundDto;
import spring.custom.api.entity.FundMstEntity;
import spring.custom.api.entity.repository.FundMstRepository;
import spring.redis.service.FundIrService;

@SpringBootApplication
@ComponentScan(basePackages = {"spring"})
@EnableJpaRepositories(basePackages = {"spring"})
@EntityScan(basePackages = {"spring"})
@EnableRedisRepositories(basePackages = {"spring"})
@EnableCaching
@EnableTransactionManagement
public class RedisSpringMain implements CommandLineRunner {
  
  public static void main(String[] args) {
    SpringApplication.run(RedisSpringMain.class, args);
  }
  
  @Autowired ObjectMapper objectMapper;
  @Autowired RedisTemplate<String, String> redisTemplate;
  @Autowired FundMstRepository fundMstRepository;
  @Resource(name="redisTemplate") ListOperations<String, String> listOps;
  @Autowired FundIrService fundIrService;
  ZSetOperations<String, String> zSetOps;
  
  @Override
  public void run(String... args) throws Exception {
    db2redis();
  }
  
  private void db2redis() {
    if (zSetOps == null) zSetOps = redisTemplate.opsForZSet();
    int chunkSize = 100;//2_500;
    int i = 0;
    Page<FundMstEntity> result = null;
    do {
      System.out.println("["+i+" page] process");
      result = fundMstRepository.findAll(PageRequest.of(i, chunkSize));
      List<FundMstEntity> allFunds = result.getContent();
      List<String> fundCds = allFunds.stream()
          .map(item -> item.getFundCd())
          .collect(Collectors.toList());
      System.out.println("fundCds: "+fundCds);
      Map<String, List<FundDto.FundIrRes>> groups = fundIrService.getAllFundIrs(fundCds);
      groups.entrySet().stream().forEach(entry -> {
        List<FundDto.FundIrRes> listYmdPrice = entry.getValue();
        // sorted(Comparator.comparing(FundDto.FundIrRes::getBasYmd)).
        listYmdPrice.stream().parallel().forEach(item -> {
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
    } while (i < result.getTotalPages() && false);
  }
}
