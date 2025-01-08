package spring.custom.common.vo;

import java.util.Map;

import org.springframework.security.core.AuthenticatedPrincipal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerVo implements AuthenticatedPrincipal {

  private String mgrId;
  private String password;
  private String mgrName;
  private String role;
  
  @Override
  public String getName() {
    return this.mgrId;
  }
  
  public static ManagerVo ofToken(Map<String, Object> userAttr) {
    return ManagerVo.builder()
        .mgrId(userAttr.getOrDefault("mgrId", "").toString())
        .mgrName(userAttr.getOrDefault("mgrName", "").toString())
        .build();
  }
  
}
