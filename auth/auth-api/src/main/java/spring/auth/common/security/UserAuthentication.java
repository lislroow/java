package spring.auth.common.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.security.AuthDetails;

@Data
public class UserAuthentication implements OAuth2User, UserDetails {
  
  private static final long serialVersionUID = 2501815366855398147L;
  
  private AuthDetails authDetails;
  
  private transient TOKEN.USER userType;
  private transient String roles;
  private transient Map<String, Object> userAttr;
  
  public UserAuthentication(TOKEN.USER userType, AuthDetails authDetails) {
    this.userType = userType;
    this.authDetails = authDetails;
    
    this.roles = authDetails.getRoles();
    this.userAttr = authDetails.toToken();
  }
  
  // [OAuth2User, UserDetails]
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authDetails.getAuthorities();
  }
  // --[OAuth2User, UserDetails]
  
  // [OAuth2User]
  @Override
  public Map<String, Object> getAttributes() {
    return this.userAttr;
  }
  // --[OAuth2User]
  
  // [AuthenticatedPrincipal]
  @Override
  public String getName() {
    return authDetails.getUsername();
  }
  // --[AuthenticatedPrincipal]
  
  
  // [UserDetails]
  @Override
  public String getPassword() {
    return authDetails.getPassword();
  }
  @Override
  public String getUsername() {
    return authDetails.getUsername();
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return authDetails.isAccountNonLocked();
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return authDetails.isCredentialsNonExpired();
  }
  @Override
  public boolean isEnabled() {
    return authDetails.isEnabled();
  }
  // --[UserDetails]
}
