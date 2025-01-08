package spring.custom.common.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationDetails extends UserDetails {
  
  Map<String, Object> toToken();
  
  String getRole();
  
  default Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(this.getRole()));
  }
  
}
