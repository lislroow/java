package spring.auth.common.login;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.auth.common.login.vo.LoginVo;
import spring.custom.common.constant.Constant;
import spring.custom.common.exception.AppException;
import spring.custom.common.syscode.ERROR;
import spring.custom.common.vo.Member;

@Slf4j
@RequiredArgsConstructor
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    /* for debug */ if (log.isInfoEnabled()) log.info("{}", authentication.getPrincipal());
    
    if (!(authentication instanceof UsernamePasswordAuthenticationToken)
        || !(authentication.getPrincipal() instanceof LoginVo.MemberDetails principal)) {
      throw new AppException(ERROR.E999);
    }
    
    Member user = principal.getUser();
    
    // create 'refresh-token'
    Map.Entry<String, String> refreshToken = tokenService.createRtk(user);
    
    // response 'rtk'
    response.addHeader(HttpHeaders.SET_COOKIE, ResponseCookie
        .from(Constant.HTTP_HEADER.X_RTK, refreshToken.getKey())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build().toString());
  }
  
}
