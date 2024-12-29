package spring.sample.config;

import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.sample.common.filter.AuthFilter;
import spring.sample.config.properties.SecurityConfigProperties;

@Configuration
public class GatewayConfig {
  
  @Bean
  GatewayFilterFactory<AuthFilter.Config> authFilter(
      SecurityConfigProperties properties) {
    return new AuthFilter(properties);
  }
}
