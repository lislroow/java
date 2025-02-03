package spring.auth.common.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberLogoutService implements LogoutHandler {

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    log.warn("[TODO] additional logout processing");
  }

}
