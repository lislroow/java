package spring.auth.api.dto;

import lombok.Data;

public class UserInfoDto {
  
  private UserInfoDto() {}
  
  @Data
  public static class MemberRes {
    private String id;
    private String loginId;
    private String nickname;
  }
  
  @Data
  public static class ManagerRes {
    private String id;
    private String loginId;
    private String mgrName;
  }
  
}
