package spring.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.enumcode.YN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo {
  
  private String tokenId;
  private String clientId;
  private String token;
  private YN enableYn;
  private YN lockedYn;
  
}
