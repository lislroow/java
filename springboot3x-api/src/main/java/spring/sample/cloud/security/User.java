package spring.sample.cloud.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
  
  private static final long serialVersionUID = -3598946512034036123L;
  
  private String id;
  private String email;
  private Role role;
  
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
        Collections.singleton(new SimpleGrantedAuthority(this.role.name()));
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
  public Role getRole() {
    return role;
  }
  public void setRole(Role role) {
    this.role = role;
  }
}