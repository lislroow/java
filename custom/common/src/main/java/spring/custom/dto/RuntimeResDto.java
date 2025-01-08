package spring.custom.dto;

import java.util.List;

import lombok.Data;
import spring.custom.initial.Classpath;

public class RuntimeResDto {
  
  private RuntimeResDto() { }
  
  @Data
  public static class BootJar {
    private List<Classpath.BootJarVo> list;
  }
}
