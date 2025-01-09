package spring.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo extends AuditVo {
  
  private String tokenId;
  private String userId;
  private String token;
  private String clientIp;
  private YN useYn;
  
}
