package spring.custom.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnsVo implements TokenPrincipal {

  private String id;
  private String roles;
  private String loginId;
  
  @Override
  public String getName() {
    return this.id;
  }
  
  @Override
  public String getUsername() {
    return this.loginId;
  }
  
}
