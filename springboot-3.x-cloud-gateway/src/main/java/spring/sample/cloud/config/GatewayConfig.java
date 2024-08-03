package spring.sample.cloud.config;

import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.sample.cloud.filter.PreJwtFilterFactory;

@Configuration
public class GatewayConfig {
  
  @Bean
  GatewayFilterFactory<PreJwtFilterFactory.Config> preJwtFilterFactory(
      SecurityConfigProperties properties) {
    return new PreJwtFilterFactory(properties);
  }
}
