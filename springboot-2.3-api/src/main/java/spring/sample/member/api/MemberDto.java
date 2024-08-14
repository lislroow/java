package spring.sample.member.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MemberInfoVo {
    private String id;
    private String name;
  }
  
}
