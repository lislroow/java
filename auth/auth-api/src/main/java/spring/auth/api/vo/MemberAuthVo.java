package spring.auth.api.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.enumcode.YN;
import spring.custom.common.security.AuthDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAuthVo implements AuthDetails {
  
  private static final long serialVersionUID = -5171902992965983740L;
  
  private String id;
  private String registrationId;
  private String oauth2Id;
  private String loginId;
  private String password;
  private String nickname;
  private String role;
  private String ip;
  private String userAgent;
  private YN memberYn;
  
  @Override
  public String getUsername() {
    return this.id;
  }
  
  @Override
  public Map<String, Object> toToken() {
    // AuthDao 의 결과값 > 'not null'
    Map<String, Object> map = Map.ofEntries(
        Map.entry("id", this.id),
        Map.entry("registrationId", this.registrationId),
        Map.entry("oauth2Id", this.oauth2Id),
        Map.entry("loginId", this.loginId),
        Map.entry("nickname", this.nickname)
        );
    return map;
  }
  
}
