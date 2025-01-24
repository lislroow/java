package spring.auth.api.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageInfo;
import spring.custom.common.syscode.YN;

public class UserMngDto {
  
  private UserMngDto() { }

  @Data
  public static class ManagerRes extends AuditVo {
    private String id;
    private String loginId;
    private String mgrName;
    private String roles;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
  }
  
  @Data
  public static class ManagerListRes {
    private List<ManagerRes> list;
    
    public ManagerListRes(List<ManagerRes> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedManagerListRes {
    private PageInfo paged;
    private List<ManagerRes> list;
  }
  
  @Data
  public static class SendRegistrationReq {
    private String toEmail;
    private String toName;
    private String grantRoles;
  }
  
  @Data
  public static class RegisterationReq {
    @NotEmpty
    private String registerCode;
    @NotEmpty
    private String newLoginPwd;
    @NotEmpty
    private String confirmLoginPwd;
  }
  
  @Data
  public static class ModifyManagerReq {
    private String id;
    private String roles;
    private YN enableYn;
    private YN lockedYn;
  }
  
  @Data
  public static class ChangePasswordReq {
    private String id;
    @Size(min = 1)
    private String currentLoginPwd;
    @Size(min = 1)
    private String newLoginPwd;
    @Size(min = 1)
    private String confirmLoginPwd;
  }
  
}
