package spring.sample.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;

@ConfigurationProperties(prefix = "spring.sample.security")
public class SecurityConfigProperties {
  
  Logger log = LoggerFactory.getLogger(SecurityConfigProperties.class);
  
  private String tokenSignkey;
  private String loginUri;

  @PostConstruct
  public void init() {
    if (this.loginUri == null || "".equals(this.loginUri))
      this.loginUri = "/v1/security/user/login";
    if (this.tokenSignkey == null || "".equals(this.tokenSignkey))
      this.tokenSignkey = "ENC(evXUYJGASPYP9NHHvTxCg1a5UFcNOFNan6AGyza4oGgd7zUVKkJgRTbOIC31wi/QZ5X7rq2HmNo=)";
  }
  
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
