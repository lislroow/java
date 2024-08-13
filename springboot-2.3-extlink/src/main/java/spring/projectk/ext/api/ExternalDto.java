package spring.projectk.ext.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ExternalDto {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ExternalInfoVo {
    private String id;
    private String name;
  }
  
}
