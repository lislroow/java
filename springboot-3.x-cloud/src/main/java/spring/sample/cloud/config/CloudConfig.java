package spring.sample.cloud.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;
import feign.RequestInterceptor;

@Configuration
@EnableFeignClients(basePackages = "spring.sample")
public class CloudConfig {

  @Bean
  Client feignClient() {
    return new Client.Default(null, null);
  }
  
  @Bean
  RequestInterceptor requestInterceptor() {
    return requestTemplate -> requestTemplate.header("hKey", "value1", "value2");
  }
}
