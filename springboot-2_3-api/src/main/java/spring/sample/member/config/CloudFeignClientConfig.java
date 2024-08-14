package spring.sample.member.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;
import feign.Retryer;

@Configuration
@EnableFeignClients(basePackages = "spring")
public class CloudFeignClientConfig {

  @Bean
  Client feignClient() {
    return new Client.Default(null, null);
  }
  
  @Bean
  Retryer retryer() {
    // 기본 생성자에서는 100ms 를 시작으로 재시작 횟수의 1.5 배로 retry 를 하며,
    // 최대 5번을 시도합니다.
    // 참고: long interval = (long) (period * Math.pow(1.5, attempt - 1));
    // 100ms, 150ms, 225ms, 337ms, 506ms
    // 2번째 인자인 SECONDS.toMillis(1) 는 최대 1000ms 를 넘기지 않도록 하는 설정입니다. 
    // this(100, SECONDS.toMillis(1), 5);
    return new Retryer.Default(1000, 2000, 3);
  }
  
}
