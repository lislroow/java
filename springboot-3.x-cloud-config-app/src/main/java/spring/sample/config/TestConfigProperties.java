package spring.sample.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@ConfigurationProperties(prefix = TestConfigProperties.PREFIX)
// curl -X POST http://localhost:8080/actuator/refresh
@RefreshScope
public class TestConfigProperties {

  public static final String PREFIX = "sample.test";
  
  private String name;
  private boolean enable;
  private List<String> list;
  private String password;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public boolean isEnable() {
    return enable;
  }
  public void setEnable(boolean enable) {
    this.enable = enable;
  }
  public List<String> getList() {
    return list;
  }
  public void setList(List<String> list) {
    this.list = list;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
}
