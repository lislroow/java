package spring.custom.common.vo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import spring.custom.common.enumcode.TOKEN;

@Data
public class UserPrincipal implements OAuth2User, UserDetails {
  
  private static final long serialVersionUID = 2501815366855398147L;
  
  private String userType;
  private transient Map<String, Object> userAttr;
  
  private String username;
  private String password;
  private String name;
  private String role;
  
  public UserPrincipal(TOKEN.USER userType, Map<String, Object> userAttr) {
    this.userType = userType.code();
    this.userAttr = userAttr;
    
    this.username = userAttr.getOrDefault("username", "").toString();
    this.password = userAttr.getOrDefault("password", "").toString();
    this.name = userAttr.getOrDefault("name", "").toString();
    this.role = userAttr.getOrDefault("role", "").toString();
    
    //userAttr.remove("password");
  }
  
  /*
  public UserPrincipal ofLogin() {
    
  }
  
  public static UserPrincipal ofToken(Map<String, Object> userAttr) {
    
  }
  */
  
  // [OAuth2User, UserDetails]
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(this.role));
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
    return this.name;
  }
  // --[AuthenticatedPrincipal]
  
  
  // [UserDetails]
  @Override
  public String getPassword() {
    return this.password;
  }
  @Override
  public String getUsername() {
    return this.username;
  }
  @Override
  public boolean isAccountNonExpired() {
    //return "N".equals(user.getDormantYn().name());
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    //return "N".equals(user.getLockedYn().name());
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    //return user.getPasswordExpireDate().isAfter(LocalDateTime.now());
    return true;
  }
  @Override
  public boolean isEnabled() {
    return isAccountNonExpired()
        && isAccountNonLocked()
        && isCredentialsNonExpired();
  }
  // --[UserDetails]
}
