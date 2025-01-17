package spring.auth.api.vo;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.util.ObjectUtils;

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
public class ManagerAuthVo implements AuthDetails {
  
  private static final long serialVersionUID = -5566207645876050761L;
  
  private String id;
  private String loginId;
  private String loginPwd;
  private String mgrName;
  private String role;
  private YN disabledYn;
  private YN lockedYn;
  private LocalDate pwdExpDate;
  
  @Override
  public String getUsername() {
    return this.id;
  }
  
  @Override
  public String getPassword() {
    return this.loginPwd;
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
    if (ObjectUtils.isEmpty(pwdExpDate)) {
      return true;
    }
    return LocalDate.now().isBefore(pwdExpDate) || !LocalDate.now().isEqual(pwdExpDate);
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
