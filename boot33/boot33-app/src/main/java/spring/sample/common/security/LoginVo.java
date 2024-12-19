package spring.sample.common.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginVo implements UserDetails {
  
  private static final long serialVersionUID = -3598946512034036123L;
  
  private Integer id;
  private String passwd;
  private String email;
  
  // [UserDetails]
  @Override
  public String getUsername() {
    return this.email;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<? extends GrantedAuthority> authorities = 
        Collections.singleton(new SimpleGrantedAuthority("ROLE_MANAGER"));
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
  @Override
  public String getPassword() {
    return this.passwd;
  }
  // --[UserDetails]
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
}