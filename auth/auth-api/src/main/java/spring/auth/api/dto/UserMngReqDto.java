package spring.auth.api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;
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
    private String password;
    private String mgrName;
    private String role;
    private YN disabledYn;
    private YN lockedYn;
  }
  
  @Data
  public static class ModifyManagerPassword {
    private String id;
    @Size(min = 1)
    private String currentLoginPwd;
    @Size(min = 1)
    private String newLoginPwd;
    @Size(min = 1)
    private String confirmLoginPwd;
  }
  
}
