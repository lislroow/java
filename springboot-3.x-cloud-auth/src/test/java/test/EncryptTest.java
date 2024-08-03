package test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;

import spring.sample.cloud.config.EncryptConfig;

public class EncryptTest extends EncryptConfig {
  
  @Test
  public void encrypt() {
    StringEncryptor enc = stringEncryptor();
    System.out.println(String.format("%s=ENC(%s)", "sample.security.token-signkey", enc.encrypt("12345678901234567890123456789012345678901234")));
  }
}