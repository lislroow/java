package spring.auth.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageInfo;

public class TokenMngDto {

  private TokenMngDto() { }
  
  @Data
  public static class TokenRes extends AuditVo {
    private String tokenId;
    private String id;
    private String clientIp;
    private YN useYn;
  }
  
  @Data
  public static class TokenListRes {
    private List<TokenRes> list;
    
    public TokenListRes(List<TokenRes> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedTokenListRes {
    private PageInfo paged;
    private List<TokenRes> list;
  }
  
}
