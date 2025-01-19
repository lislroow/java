package spring.custom.common.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthDetails extends UserDetails {
  
  Map<String, Object> toToken();
  
  String getRoles();
  
  default Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(this.getRoles().split(","))
        .map(String::trim)
        .filter(role -> !role.isEmpty())
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
  }
  
}
