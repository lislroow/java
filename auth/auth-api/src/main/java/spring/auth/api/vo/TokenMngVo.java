package spring.auth.api.vo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageRequest;


public class TokenMngVo {
  
  private TokenMngVo() { }
  
  @Data
  @Builder
  public static class SearchParam extends PageRequest {
    private String clientId;
    private String tokenKey;
    private String contactName;
    private YN enableYn;
  }
  
  @Data
  @Builder
  public static class SearchResult extends AuditVo {
    private String contactName;
    private String contactEmail;
    private String tokenKey;
    private String clientId;
    private String clientIp;
    private String clientName;
    private String roles;
    private YN enableYn;
    private LocalDate expDate;
  }
  
  @Data
  public static class AddTokenClient extends AuditVo {
    private String tokenKey;
    private String clientId;
    private String clientName;
    private String clientIp;
    private String roles;
    private YN enableYn;
    private LocalDate expDate;
    private String tokenValue;
  }
  
  @Data
  public static class ModifyTokenClient extends AuditVo {
    private String tokenKey;
    private YN enableYn;
    private LocalDate expDate;
  }
  
}
