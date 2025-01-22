package spring.auth.common.login;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.security.LoginDetails;
import spring.custom.common.vo.TokenPrincipal;

@Data
public class UserAuthentication<T extends TokenPrincipal, S extends LoginDetails<?>> 
    implements OAuth2User, UserDetails {
  
  private static final long serialVersionUID = 2501815366855398147L;
  
  private S loginDetails;
  
  private transient TOKEN.USER userType;
  private transient T principal;
  
  @SuppressWarnings("unchecked")
  public UserAuthentication(TOKEN.USER userType) {
    this.userType = userType;
  }
  
  @SuppressWarnings("unchecked")
  public UserAuthentication(TOKEN.USER userType, S loginDetails) {
    this.userType = userType;
    this.loginDetails = loginDetails;
    this.principal = (T) loginDetails.toPrincipal();
  }
  
  public String getRoles() {
    return loginDetails.getRoles();
  }
  
  // [OAuth2User, UserDetails]
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return loginDetails == null ? AuthorityUtils.NO_AUTHORITIES : loginDetails.getAuthorities();
  }
  // --[OAuth2User, UserDetails]
  
  // [OAuth2User]
  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }
  // --[OAuth2User]
  
  // [AuthenticatedPrincipal]
  @Override
  public String getName() {
    return loginDetails.getUsername();
  }
  // --[AuthenticatedPrincipal]
  
  
  // [UserDetails]
  @Override
  public String getPassword() {
    return loginDetails.getPassword();
  }
  @Override
  public String getUsername() {
    return loginDetails.getUsername();
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return loginDetails.isAccountNonLocked();
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return loginDetails.isCredentialsNonExpired();
  }
  @Override
  public boolean isEnabled() {
    return loginDetails.isEnabled();
  }
  // --[UserDetails]
}
