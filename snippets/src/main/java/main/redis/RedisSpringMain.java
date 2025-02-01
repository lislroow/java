package main.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
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
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.annotation.Resource;
import spring.custom.api.entity.FundMstEntity;
import spring.custom.api.entity.repository.FundMstRepository;
import spring.redis.repository.FundMstRedis;
import spring.redis.vo.FundMstRvo;

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
  
  @Autowired ModelMapper modelMapper;
  @Autowired RedisTemplate<String, String> redisTemplate;
  @Autowired FundMstRepository fundMstRepository;
  @Autowired FundMstRedis fundMstRedis;
  @Resource(name="redisTemplate") ListOperations<String, String> listOps;
  
  @Override
  public void run(String... args) throws Exception {
    test1();
    test2();
  }
  
  private void test2() {
    Iterable<FundMstRvo> iter = fundMstRedis.findAll();
    List<Long> listAdded = StreamSupport.stream(iter.spliterator(), false)
        .map(item -> listOps.rightPush("fund:fundCd-list", item.getFundCd()))
        .collect(Collectors.toList());
    String firstOne = listOps.getFirst("fund:fundCd-list");
    System.out.println("firstOne: " + firstOne);
    // start 는 0 부터
    // 2 ~ 4 는 3개가 반환
    List<String> list2_4 = listOps.range("fund:fundCd-list", 2, 4);
    System.out.println("list2_4: " + list2_4);
  }
  
  private void test1() {
    List<FundMstRvo> list = this.findAllFromDB();
    List<FundMstRvo> newList = new ArrayList<>(list.subList(0, Math.min(10000, list.size())));
    long st = System.currentTimeMillis();
    //this.saveListToRedis(newList);
    this.saveListToRedis(list);
    System.out.println((+System.currentTimeMillis() - st)+":fundMstRedis.saveAll()");
    st = System.currentTimeMillis();
    Optional<FundMstRvo> redisKR5307828024 = fundMstRedis.findById("KR5307828024");
    System.out.println((+System.currentTimeMillis() - st)+":fundMstRedis.findById()");
    if (redisKR5307828024.isPresent()) System.out.println(redisKR5307828024.get());
  }
  
  private void saveListToRedis(List<FundMstRvo> list) {
    fundMstRedis.saveAll(list);
  }
  
  private List<FundMstRvo> findAllFromDB() {
    long st = System.currentTimeMillis();
    List<FundMstEntity> result = fundMstRepository.findAll();
    System.out.println((+System.currentTimeMillis() - st)+":fundMstRepository.findAll()");
    st = System.currentTimeMillis();
    List<FundMstRvo> list = result.stream()
        .map(item -> modelMapper.map(item, FundMstRvo.class))
        .collect(Collectors.toList());
    System.out.println((+System.currentTimeMillis() - st)+":modelMapper.map()");
    return list;
  }
}
