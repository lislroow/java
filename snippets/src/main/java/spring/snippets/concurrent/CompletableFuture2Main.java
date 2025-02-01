package spring.snippets.concurrent;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;
import spring.custom.api.dto.JpaSampleDto;
import spring.custom.api.entity.SatelliteEntity;
import spring.custom.api.entity.repository.SatelliteRepository;
import spring.custom.common.redis.RedisClient;

@SpringBootApplication
@ComponentScan(basePackages = {"spring"})
@EnableJpaRepositories(basePackages = {"spring"})
@EntityScan(basePackages = {"spring"})
@EnableCaching
@EnableTransactionManagement
@Slf4j
public class CompletableFuture2Main implements CommandLineRunner {
  
  public static void main(String[] args) {
    SpringApplication.run(CompletableFuture2Main.class, args);
  }
  
  @Autowired ModelMapper modelMapper;
  @Autowired(required = false) SatelliteRepository satelliteRepository;
  @Autowired(required = false) RedisClient redisClient;
  
  @Override
  public void run(String... args) throws Exception {
    List<JpaSampleDto.SatelliteRes> list = this.findAll();
    System.out.println(list);
  }
  
  @Cacheable(value = "cache:satellite:all")
  private List<JpaSampleDto.SatelliteRes> findAll() {
    List<SatelliteEntity> result = satelliteRepository.findAll();
    List<JpaSampleDto.SatelliteRes> list = result.stream()
        .map(item -> modelMapper.map(item, JpaSampleDto.SatelliteRes.class))
        .collect(Collectors.toList());
    return list;
  }
  
  /*
  public static void main(String[] args) {
    
    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        Thread.currentThread().interrupt();
      }
      return "첫 번째 작업 완료!";
    });
    
    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        Thread.currentThread().interrupt();
      }
      return "두 번째 작업 완료!";
    });
    
    CompletableFuture<Void> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
      log.info(result1);
      log.info(result2);
      return null;
    });
    
    combinedFuture.join();  // 모든 작업이 끝날 때까지 대기
    
    log.info("최종 완료");
  }
  */
}
