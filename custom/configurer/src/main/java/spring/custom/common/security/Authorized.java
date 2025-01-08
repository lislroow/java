package spring.custom.common.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service(value = "authorized")
public class Authorized {

  //@PreAuthorize("hasRole('MANAGER') and @authorized.isOwner(#reqDto.userId)")
  //@PreAuthorize("@authorized.isOwner(#reqDto.userId)")
  public boolean isOwner(String userId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    System.err.println(username);
    return userId != null && username.equals(userId);
  }
  
}
