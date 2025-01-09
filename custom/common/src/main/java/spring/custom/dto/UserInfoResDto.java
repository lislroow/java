package spring.custom.dto;

import lombok.Data;

public class UserInfoResDto {
  
  private UserInfoResDto() {}
  
  @Data
  public static class MemberInfo {
    private String email;
    private String nickname;
  }
  
  @Data
  public static class ManagerInfo {
    private String mgrId;
    private String mgrName;
  }
  
}
