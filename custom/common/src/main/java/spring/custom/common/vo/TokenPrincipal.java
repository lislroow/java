package spring.custom.common.vo;

import java.util.Optional;

import org.springframework.security.core.AuthenticatedPrincipal;

import spring.custom.common.enumcode.TOKEN;


public interface TokenPrincipal extends AuthenticatedPrincipal {
  
  String getId();
  String getRoles();
  String getLoginId();
  String getUsername();
  
  default Optional<TOKEN.USER_TYPE> getUserType() {
    Integer idprefix = Integer.parseInt(this.getId().substring(0, 1));
    return TOKEN.USER_TYPE.fromIdprefix(idprefix);
  }
  
}
