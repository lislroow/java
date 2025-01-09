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
  
  private AuthDetails tokenUserDetails;
  
  private transient TOKEN.USER userType;
  private transient String role;
  private transient Map<String, Object> userAttr;
  
  public UserAuthentication(TOKEN.USER userType, AuthDetails tokenUserDetails) {
    this.userType = userType;
    this.tokenUserDetails = tokenUserDetails;
    this.role = tokenUserDetails.getRole();
    this.userAttr = tokenUserDetails.toToken();
  }
  
  // [OAuth2User, UserDetails]
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return tokenUserDetails.getAuthorities();
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
    return tokenUserDetails.getUsername();
  }
  // --[AuthenticatedPrincipal]
  
  
  // [UserDetails]
  @Override
  public String getPassword() {
    return tokenUserDetails.getPassword();
  }
  @Override
  public String getUsername() {
    return tokenUserDetails.getUsername();
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return tokenUserDetails.isAccountNonLocked();
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return tokenUserDetails.isCredentialsNonExpired();
  }
  @Override
  public boolean isEnabled() {
    return tokenUserDetails.isEnabled();
  }
  // --[UserDetails]
}
