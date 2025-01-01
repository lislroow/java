package spring.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = JwtProperties.PREFIX)
@Data
public class JwtProperties {

  public static final String PREFIX = "jwt";
  private Cert cert = new Cert();
  private Token token = new Token();
  
  @Data
  public class Cert {
    private String publicKeyFilePath = "C:\\project\\java\\auth\\auth-api\\config\\cert\\star.develop.net.crt";
    private String privateKeyFilePath = "C:\\project\\java\\auth\\auth-api\\config\\cert\\star.develop.net.key";
  }
  
  @Data
  public class Token {
    private String issuer = "market.develop.net";
    private Long accessTokenExpireTime = 600L;
    private Long refreshTokenExpireTime = 86400L;
  }
}
