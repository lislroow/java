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
public class MemberVo implements AuthenticatedPrincipal {
  
  private String id;
  private String registrationId;
  private String oauth2Id;
  private String email;
  private String password;
  private String nickname;
  private String role;
  private String ip;
  private String userAgent;
  
  public static MemberVo ofToken(Map<String, Object> userAttr) {
    return MemberVo.builder()
        .id(userAttr.getOrDefault("id", "").toString())
        .registrationId(userAttr.getOrDefault("registrationId", "").toString())
        .oauth2Id(userAttr.getOrDefault("oauth2Id", "").toString())
        .email(userAttr.getOrDefault("email", "").toString())
        .nickname(userAttr.getOrDefault("nickname", "").toString())
        .build();
  }

  @Override
  public String getName() {
    return this.email;
  }
  
}
