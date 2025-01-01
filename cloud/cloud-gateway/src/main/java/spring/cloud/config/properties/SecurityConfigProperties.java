package spring.cloud.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "spring.sample.security")
@Data
public class SecurityConfigProperties {
  
  private String tokenSignkey;
  private String loginUri;
  
}
