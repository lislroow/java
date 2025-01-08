package spring.auth.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.enumcode.YN;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenVo extends AuditVo {
  
  private String id;
  private String token;
  private String clientIp;
  private YN useYn;
  
}
