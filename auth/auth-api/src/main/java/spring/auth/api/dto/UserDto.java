package spring.auth.api.dto;

import lombok.Data;

public class UserDto {
  
  private UserDto() {}
  
  @Data
  public static class InfoRes {
    private String id;
    private String roles;
    private String loginId;
    private String username;
  }
  
}
