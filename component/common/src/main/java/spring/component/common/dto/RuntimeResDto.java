package spring.component.common.dto;

import java.util.List;

import lombok.Data;
import spring.component.common.vo.BootJarVo;

public class RuntimeResDto {
  
  private RuntimeResDto() { }
  
  @Data
  public static class BootJar {
    private List<BootJarVo> list;
  }
}
