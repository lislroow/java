package spring.auth.api.vo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import spring.custom.code.EnumYN;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.vo.AuditVo;


public class TokenMngVo {
  
  private TokenMngVo() { }
  
  @Data
  @Builder
  public static class SearchParam extends PageRequest {
    private String clientId;
    private String tokenKey;
    private String contactName;
    private EnumYN enableYn;
  }
  
  @Data
  public static class ResultTokenClient extends AuditVo {
    private String contactName;
    private String contactEmail;
    private String tokenKey;
    private String clientId;
    private String clientIp;
    private String clientName;
    private String roles;
    private EnumYN enableYn;
    private LocalDate expDate;
  }
  
  @Data
  public static class AddTokenClient extends AuditVo {
    private String tokenKey;
    private String clientId;
    private String clientName;
    private String clientIp;
    private String roles;
    private EnumYN enableYn;
    private LocalDate expDate;
    private String tokenValue;
  }
  
  @Data
  public static class ModifyTokenClient extends AuditVo {
    private String tokenKey;
    private EnumYN enableYn;
    private LocalDate expDate;
  }
  
}
