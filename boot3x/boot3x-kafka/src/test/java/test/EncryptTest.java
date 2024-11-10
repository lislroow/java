package test;

import java.util.stream.IntStream;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;

import spring.sample.config.EncryptConfig;
import spring.sample.util.Uuid;

public class EncryptTest extends EncryptConfig {
  
  @Test
  public void encrypt() {
    StringEncryptor enc = stringEncryptor();
    System.out.println(String.format("%s: ENC(%s)", "sample.jdbc.datasource.username", enc.encrypt("scott")));  
    System.out.println(String.format("%s: ENC(%s)", "sample.jdbc.datasource.password", enc.encrypt("tiger")));  
  }
  
  @Test
  public void decrypt() {
    StringEncryptor enc = stringEncryptor();
    System.out.println(String.format("%s: %s", "sample.jdbc.datasource.username", enc.decrypt("uGLITJhOts9qjZPlHoedlw==")));  
    System.out.println(String.format("%s: %s", "sample.jdbc.datasource.password", enc.decrypt("zdeXu2jkMYZCk61PLZPVPA==")));  
  }
  
  @Test
  public void createUuid() {
    IntStream.range(0, 3).forEach(item -> {
      System.out.println(String.format("'%s'", Uuid.create()));
    });
  }
}