package spring.sample.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SecurityConfigProperties.PREFIX)
public class SecurityConfigProperties {
  
  Logger log = LoggerFactory.getLogger(MybatisProperties.class);
  
  public static final String PREFIX = "sample.security";
  
  private String tokenSignkey;
  private String loginUri;
  
  public String getTokenSignkey() {
    return tokenSignkey;
  }
  public void setTokenSignkey(String tokenSignkey) {
    this.tokenSignkey = tokenSignkey;
  }
  public String getLoginUri() {
    return loginUri;
  }
  public void setLoginUri(String loginUri) {
    this.loginUri = loginUri;
  }
}
