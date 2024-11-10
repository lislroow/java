package spring.sample.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;

import feign.Client;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@Configuration
public class CloudFeignClientConfig implements Jackson2ObjectMapperBuilderCustomizer {

  @Bean
  Client feignClient() {
    return new Client.Default(null, null);
  }
  
  @Bean
  RequestInterceptor requestInterceptor() {
    return requestTemplate -> requestTemplate.header("hKey", "value1", "value2");
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
  
  @Bean
  ErrorDecoder decoder() {
    return (methodKey, response) -> {
      if (HttpStatus.INTERNAL_SERVER_ERROR.value() == response.status()) {
        //return new RetryableException(); // TODO
        System.err.println("return new RetryableException(); // TODO");
      }
      System.err.println("return new IllegalStateException(); // TODO");
      return new IllegalStateException(); // TODO
    };
  }
  
  @Override
  public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
    // tolerance 추가 (제약을 강화하거나 약화할 경우 설정할 수 있음) 
    jacksonObjectMapperBuilder
      .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
      .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }
}
