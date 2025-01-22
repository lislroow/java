package spring.custom.common.vo;

import org.springframework.security.core.AuthenticatedPrincipal;


public interface Principal extends AuthenticatedPrincipal {
  
  String getUserType();
  String getId();
  String getRoles();
  String getLoginId();
  
}
