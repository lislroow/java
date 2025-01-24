package spring.auth.api.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.code.EnumYN;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageRequest;

public class UserMngVo {
  
  private UserMngVo() { }
  
  @Data
  @Builder
  public static class SearchParam extends PageRequest {
    private String loginId;
    private String mgrName;
    private String roles;
    private EnumYN enableYn;
    private EnumYN lockedYn;
  }
  
  @Data
  public static class ResultManager extends AuditVo {
    private String id;
    private String loginId;
    private String loginPwd;
    private String mgrName;
    private String roles;
    private EnumYN enableYn;
    private EnumYN lockedYn;
    private LocalDate pwdExpDate;
  }
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AddManager extends AuditVo {
    private String id;
    private String loginId;
    private String loginPwd;
    private String mgrName;
    private String roles;
    private EnumYN enableYn;
    private EnumYN lockedYn;
    private LocalDate pwdExpDate;
  }
  
  @Data
  public static class ModifyManager extends AuditVo {
    private String id;
    private String loginPwd;
    private String mgrName;
    private String roles;
    private EnumYN enableYn;
    private EnumYN lockedYn;
  }
  
}
