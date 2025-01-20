package spring.auth.api.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageInfo;

public class TokenMngDto {

  private TokenMngDto() { }
  
  @Data
  public static class ClientTokenRes extends AuditVo {
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
  public static class ClientTokenListRes {
    private List<ClientTokenRes> list;
    
    public ClientTokenListRes(List<ClientTokenRes> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedClientTokenListRes {
    private PageInfo paged;
    private List<ClientTokenRes> list;
  }
  
  @Data
  public static class AddTokenClientReq {
    private String clientId;
    private String clientName;
    private String clientIp;
    private String roles;
    private YN enableYn;
    private LocalDate expDate;
  }
  
  @Data
  public static class ModifyTokenClientReq {
    private String tokenKey;
    private YN enableYn;
    private LocalDate expDate;
  }
  
}
