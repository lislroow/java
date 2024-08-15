package spring.sample.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.commons.httpclient.OkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Retryer;
import okhttp3.ConnectionPool;
import spring.sample.config.feign.FeignInterceptor;

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureAfter(FeignAutoConfiguration.class)
@EnableFeignClients(basePackages = {"spring"})
public class FeignConfig {

  @Bean
  @ConditionalOnMissingBean({ConnectionPool.class})
  ConnectionPool httpClientConnectionPool(FeignHttpClientProperties httpClientProperties, OkHttpClientConnectionPoolFactory okHttpClientConnectionPoolFactory) {
    return okHttpClientConnectionPoolFactory.create(httpClientProperties.getMaxConnections(), httpClientProperties.getTimeToLive(), httpClientProperties.getTimeToLiveUnit());
  }

  @Bean
  okhttp3.OkHttpClient okHttpClient(OkHttpClientFactory okHttpClientFactory, ConnectionPool connectionPool, FeignHttpClientProperties feignHttpClientProperties) {
    return okHttpClientFactory.createBuilder(feignHttpClientProperties.isDisableSslValidation())
        .connectTimeout(55, TimeUnit.SECONDS)
        .followRedirects(feignHttpClientProperties.isFollowRedirects())
        .connectionPool(connectionPool)
        .addInterceptor(new FeignInterceptor())
        .build();
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
