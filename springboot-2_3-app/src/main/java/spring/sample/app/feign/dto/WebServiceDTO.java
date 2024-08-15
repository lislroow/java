package spring.sample.app.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class WebServiceDTO {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MemberVO {
    private String id;
    private String name;
  }
  
}
