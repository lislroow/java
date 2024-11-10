package spring.sample.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {
  
  @Value("${jasypt.encryptor.password:123}") // :(콜론)은 기본값을 정의할 때 사용함
  private String PASSWORD_KEY;
  
  @Bean("jasyptStringEncryptor")
  public StringEncryptor stringEncryptor(){
    if (PASSWORD_KEY == null) {
      PASSWORD_KEY = "123";  // junit 테스트 시 -Djasypt.encryptor.password=123 옵션이 추가되어야 하지만 편의상 "123" 하드코딩
    }
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(PASSWORD_KEY);
    config.setPoolSize("1");
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setStringOutputType("base64");
    config.setKeyObtentionIterations("1000");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    encryptor.setConfig(config);
    return encryptor;
  }
}
