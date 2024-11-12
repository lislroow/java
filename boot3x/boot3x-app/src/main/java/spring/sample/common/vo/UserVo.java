package spring.sample.common.vo;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import spring.sample.common.enumcode.SECURITY_ROLE_CD;

public class UserVo implements UserDetails {
  
  private static final long serialVersionUID = -3598946512034036123L;
  
  private String id;
  private String email;
  private SECURITY_ROLE_CD securityRoleCd;
  
  // [UserDetails]
  @Override
  public String getUsername() {
    return this.email;
  }
  @Override
  public String getPassword() {
    return null;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<? extends GrantedAuthority> authorities = 
        Collections.singleton(new SimpleGrantedAuthority(this.securityRoleCd.code()));
    return authorities;
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override
  public boolean isEnabled() {
    boolean isEnabled = isAccountNonExpired()
        && isAccountNonLocked()
        && isCredentialsNonExpired();
    return isEnabled;
  }
  // --[UserDetails]
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public SECURITY_ROLE_CD getRole() {
    return securityRoleCd;
  }
  public void setRole(SECURITY_ROLE_CD securityRoleCd) {
    this.securityRoleCd = securityRoleCd;
  }
}