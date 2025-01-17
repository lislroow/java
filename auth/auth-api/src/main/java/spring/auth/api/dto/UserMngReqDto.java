package spring.auth.api.dto;

import java.time.LocalDateTime;

import lombok.Data;
import spring.custom.common.enumcode.YN;

public class UserMngReqDto {
  
  private UserMngReqDto() { }

  @Data
  public static class AddManager {
    private String id;
    private String loginId;
    private String password;
    private String mgrName;
    private String role;
    private YN disabledYn;
    private YN lockedYn;
    private LocalDateTime pwdExpTime;
  }
  
  @Data
  public static class ModifyManager {
    private String id;
    private String mgrName;
    private String role;
    private YN disabledYn;
    private YN lockedYn;
  }
  
}
