package spring.custom.common.vo;

import java.security.Principal;


public interface User extends Principal {
  
  String getUserType();
  String getId();
  String getRoles();
  String getLoginId();
  
  default String getName() {
    return getId();
  }
  
}
