package spring.sample.config;

import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.sample.common.filter.AuthFilterFactory;
import spring.sample.config.properties.SecurityConfigProperties;

@Configuration
public class GatewayConfig {
  
//  @Bean
//  GatewayFilterFactory<AuthFilterFactory.Config> authFilterFactory(
//      SecurityConfigProperties properties) {
//    return new AuthFilterFactory(properties);
//  }
}
