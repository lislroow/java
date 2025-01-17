package spring.auth.api.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageRequest;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class UserMngVo extends AuditVo {
  
  private String id;
  private String loginId;
  private String password;
  private String mgrName;
  private String role;
  private YN disabledYn;
  private YN lockedYn;
  private LocalDate pwdExpDate;
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class SearchVo extends PageRequest {
    private String loginId;
    private String mgrName;
    private String role;
    private YN disabledYn;
    private YN lockedYn;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class AddVo extends AuditVo {
    private String id;
    private String loginId;
    private String password;
    private String mgrName;
    private String role;
    private YN disabledYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ModifyVo extends AuditVo {
    private String id;
    private String mgrName;
    private String role;
    private YN disabledYn;
    private YN lockedYn;
  }
}
