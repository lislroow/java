package spring.sample.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import spring.sample.config.properties.SecurityConfigProperties;

@Configuration
@EnableConfigurationProperties({SecurityConfigProperties.class})
public class SecurityConfig {
  
}
