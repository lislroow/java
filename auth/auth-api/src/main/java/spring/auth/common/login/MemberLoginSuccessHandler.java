package spring.auth.common.login;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.MemberPrincipal;

@Slf4j
@RequiredArgsConstructor
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    /* for debug */ if (log.isInfoEnabled()) log.info("{}", authentication.getPrincipal());
    
    if (!(authentication instanceof UserAuthentication userAuthentication)
        || !(userAuthentication.getPrincipal() instanceof MemberPrincipal principal)) {
      throw new AppException(ERROR.E999);
    }
    
    // create 'refresh-token'
    Map.Entry<String, String> refreshToken = tokenService.createRtk(principal);
    
    // response 'rtk'
    response.addHeader(HttpHeaders.SET_COOKIE, ResponseCookie
        .from(Constant.HTTP_HEADER.X_RTK, refreshToken.getKey())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build().toString());
  }
  
}
