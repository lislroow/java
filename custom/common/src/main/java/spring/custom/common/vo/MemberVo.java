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
public class MemberVo implements AuthenticatedPrincipal {
  
  private String id;
  private String registrationId;
  private String oauth2Id;
  private String loginId;
  private String password;
  private String nickname;
  private String role;
  private String ip;
  private String userAgent;
  
  @Override
  public String getName() {
    return this.id;
  }
  
  public static MemberVo ofToken(Map<String, Object> userAttr) {
    if (ObjectUtils.isEmpty(userAttr.containsKey("id"))) {
      throw new AppException(ERROR.A009);
    }
    return MemberVo.builder()
        .id(userAttr.get("id").toString())
        .registrationId(userAttr.getOrDefault("registrationId", "").toString())
        .oauth2Id(userAttr.getOrDefault("oauth2Id", "").toString())
        .loginId(userAttr.getOrDefault("loginId", "").toString())
        .nickname(userAttr.getOrDefault("nickname", "").toString())
        .build();
  }
  
}
