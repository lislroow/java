package spring.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenMngVo extends AuditVo {

  private String tokenId;
  private String id;
  private String clientIp;
  private YN useYn;
  private String token;
  
  @Data
  @Builder
  public static class SearchVo extends PageRequest {
    private String tokenId;
    private String id;
    private String clientIp;
    private YN useYn;
  }
  
}
