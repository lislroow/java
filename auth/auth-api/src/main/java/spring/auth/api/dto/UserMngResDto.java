package spring.auth.api.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageInfo;

public class UserMngResDto {
  
  private UserMngResDto() { }
  
  @Data
  public static class Manager extends AuditVo {
    private String id;
    private String loginId;
    private String mgrName;
    private String role;
    private YN disabledYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
  }
  
  @Data
  public static class ManagerList {
    private List<Manager> list;
    
    public ManagerList(List<Manager> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedManagerList {
    private PageInfo paged;
    private List<Manager> list;
  }

}
