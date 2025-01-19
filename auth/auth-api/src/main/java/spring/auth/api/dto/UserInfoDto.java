package spring.auth.api.dto;

import lombok.Data;

public class UserInfoDto {
  
  private UserInfoDto() {}
  
  @Data
  public static class UserRes {
    private String id;
    private String roles;
    private String loginId;
    private String username;
  }
  
}
