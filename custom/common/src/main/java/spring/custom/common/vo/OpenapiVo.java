package spring.custom.common.vo;

import java.util.Map;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenapiVo implements AuthenticatedPrincipal {
  
  private String id;
  private String loginId;
  private String userName;
  
  @Override
  public String getName() {
    return this.id;
  }
  
  public static OpenapiVo ofToken(Map<String, Object> userAttr) {
    if (ObjectUtils.isEmpty(userAttr.containsKey("id"))) {
      throw new AppException(ERROR.A009);
    }
    return OpenapiVo.builder()
        .id(userAttr.get("id").toString())
        .loginId(userAttr.getOrDefault("loginId", "").toString())
        .userName(userAttr.getOrDefault("userName", "").toString())
        .build();
  }
  
}
