package spring.auth.api.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.security.AuthDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties("passwd")
public class ManagerAuthVo implements AuthDetails {
  
  private static final long serialVersionUID = -5566207645876050761L;

  private String id;
  private String loginId;
  private String password;
  private String mgrName;
  private String role;
  
  @Override
  public String getUsername() {
    return this.id;
  }
  
  @Override
  public Map<String, Object> toToken() {
    // AuthDao 의 결과값 > 'not null' 
    Map<String, Object> map = Map.ofEntries(
        Map.entry("id", this.id),
        Map.entry("loginId", this.loginId),
        Map.entry("mgrName", this.mgrName)
        );
    return map;
  }
  
}
