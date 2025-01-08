package spring.custom.dto;

import lombok.Data;

public class MemberResDto {
  
  private MemberResDto() {}
  
  @Data
  public static class Info {
    private String email;
    private String nickname;
  }
  
}
