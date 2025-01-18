package spring.auth.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spring.custom.common.enumcode.YN;

public class UserMngReqDto {
  
  private UserMngReqDto() { }

  @Data
  public static class SendRegistration {
    private String toEmail;
    private String toName;
    private String grantRole;
  }
  
  @Data
  public static class Registeration {
    @NotEmpty
    private String registerCode;
    @NotEmpty
    private String newLoginPwd;
    @NotEmpty
    private String confirmLoginPwd;
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
