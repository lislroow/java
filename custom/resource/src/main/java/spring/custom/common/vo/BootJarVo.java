package spring.custom.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BootJarVo {
  
  private String jar;
  private String version;
  
}