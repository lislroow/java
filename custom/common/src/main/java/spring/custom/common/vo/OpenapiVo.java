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
public class OpenapiVo implements AuthenticatedPrincipal {
  
  private String userId;
  private String userName;
  
  @Override
  public String getName() {
    return this.userId;
  }
  
  public static OpenapiVo ofToken(Map<String, Object> userAttr) {
    return OpenapiVo.builder()
        .userId(userAttr.getOrDefault("userId", "").toString())
        .userName(userAttr.getOrDefault("userName", "").toString())
        .build();
  }
  
}
