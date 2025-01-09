package spring.auth.api.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties("passwd")
public class MemberAuthVo implements AuthDetails {
  
  private static final long serialVersionUID = -5171902992965983740L;
  
  private String id;
  private String registrationId;
  private String oauth2Id;
  private String email;
  private String password;
  private String nickname;
  private String role;
  private String ip;
  private String userAgent;
  private YN memberYn;
  
  @Override
  public String getUsername() {
    return this.email;
  }
  
  @Override
  public Map<String, Object> toToken() {
    // AuthDao 의 결과값 > 'not null'
    Map<String, Object> map = Map.ofEntries(
        Map.entry("registrationId", this.registrationId),
        Map.entry("oauth2Id", this.oauth2Id),
        Map.entry("email", this.email),
        Map.entry("nickname", this.nickname)
        );
    return map;
  }
  
}
