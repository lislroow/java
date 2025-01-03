package spring.custom.common.vo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

@Data
public class AuthPrincipal implements OAuth2User, UserDetails {
  
  private static final long serialVersionUID = 2501815366855398147L;

  private String username;
  private String nickname;
  private String role;
  private String ip;
  private String userAgent;
  
  //private User user;
  private transient Map<String,Object> attributes;
  
  public AuthPrincipal(MemberVo memberVo) {
    this.username = memberVo.getEmail();
    this.nickname = memberVo.getNickname();
    this.role = memberVo.getRole();
    this.ip = memberVo.getIp();
    this.userAgent = memberVo.getUserAgent();
  }
  
  // [OAuth2User, UserDetails]
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(this.role));
  }
  // --[OAuth2User, UserDetails]
  
  // [OAuth2User]
  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }
  // --[OAuth2User]
  
  // [AuthenticatedPrincipal]
  @Override
  public String getName() {
    return this.nickname;
  }
  // --[AuthenticatedPrincipal]
  
  
  // [UserDetails]
  @Override
  public String getPassword() {
    //return user.getPassword();
    return "";
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
