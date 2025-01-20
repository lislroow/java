package spring.custom.common.security;

//import java.util.Arrays;
import java.util.Collection;
//import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import spring.custom.common.vo.TokenPrincipal;

public interface LoginDetails<T extends TokenPrincipal> extends UserDetails {
  
  T toPrincipal();
  
  String getRoles();
  
  default Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(this.getRoles());
    //return Arrays.stream(this.getRoles().split(","))
    //    .map(String::trim)
    //    .filter(role -> !role.isEmpty())
    //    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
    //    .collect(Collectors.toList());
  }
  
  Long getRefreshExpireTime();
  Long getAccessExpireTime();
  
}
