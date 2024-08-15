package spring.sample.egress.dto;

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
