package spring.auth.api.vo;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.util.ObjectUtils;

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
public class ManagerAuthVo implements AuthDetails {
  
  private static final long serialVersionUID = -5566207645876050761L;

  private String id;
  private String loginId;
  private String password;
  private String mgrName;
  private String role;
  private YN disabledYn;
  private YN lockedYn;
  private LocalDateTime pwdExpTime;
  
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
  
  @Override
  public boolean isCredentialsNonExpired() {
    if (ObjectUtils.isEmpty(pwdExpTime)) {
      return true;
    }
    return LocalDateTime.now().isBefore(pwdExpTime);
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return lockedYn.compareTo(YN.N) == 0;
  }

  @Override
  public boolean isEnabled() {
    return disabledYn.compareTo(YN.N) == 0;
  }
  
}
