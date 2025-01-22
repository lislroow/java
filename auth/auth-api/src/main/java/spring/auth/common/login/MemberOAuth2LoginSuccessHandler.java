package spring.auth.common.login;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.auth.common.login.vo.LoginVo;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.Member;

@Slf4j
@RequiredArgsConstructor
public class MemberOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
  
  final TokenService tokenService;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    /* for debug */ if (log.isInfoEnabled()) log.info("{}", authentication.getPrincipal());
    
    if (!(authentication instanceof OAuth2AuthenticationToken oauth2)
        || !(oauth2.getPrincipal() instanceof LoginVo.MemberOAuth2 principal)) {
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
    response.setHeader(HttpHeaders.LOCATION, "/");
    response.setStatus(HttpStatus.FOUND.value());
  }
  
}
