package spring.custom.dto;

import java.util.List;

import lombok.Data;
import spring.custom.initial.Classpath;

public class RuntimeDto {
  
  private RuntimeDto() { }
  
  @Data
  public static class BootJarRes {
    private List<Classpath.BootJarVo> list;
  }
}
