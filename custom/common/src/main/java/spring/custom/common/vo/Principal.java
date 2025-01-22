package spring.custom.common.vo;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;


public interface Principal extends OAuth2User {
  
  String getUserType();
  String getId();
  String getRoles();
  String getLoginId();
  
  default Map<String, Object> getAttributes() {
    return null;
  }

  default Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
  
}
