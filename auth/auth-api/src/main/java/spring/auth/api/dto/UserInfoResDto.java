package spring.auth.api.dto;

import lombok.Data;

public class UserInfoResDto {
  
  private UserInfoResDto() {}
  
  @Data
  public static class MemberInfo {
    private String id;
    private String loginId;
    private String nickname;
  }
  
  @Data
  public static class ManagerInfo {
    private String id;
    private String loginId;
    private String mgrName;
  }
  
}
