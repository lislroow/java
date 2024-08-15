package spring.sample.app.feign.dto;

import lombok.Builder;
import lombok.Data;

public class WebServiceDTO {

  @Data
  @Builder
  public static class MemberVO {
    private String id;
    private String name;
  }
  
}
