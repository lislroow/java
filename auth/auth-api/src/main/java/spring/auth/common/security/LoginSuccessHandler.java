package spring.auth.common.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.vo.AuthPrincipal;
import spring.custom.dto.TokenResDto;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    Assert.isTrue(authentication.getPrincipal() != null, "authentication.getPrincipal() is null");
    Assert.isTrue(authentication.getPrincipal() instanceof AuthPrincipal, "authentication.getPrincipal() is not SessionUser type");
    
    String ip = request.getRemoteAddr();
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    TokenResDto.Create resDto = tokenService.createToken(TOKEN.USER.MEMBER, authentication, ip, userAgent);
    ResponseCookie rtkCookie = ResponseCookie.from(Constant.HTTP_HEADER.X_RTKID, resDto.getRtkUuid())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build();
    response.addHeader(HttpHeaders.SET_COOKIE, rtkCookie.toString());
  }
}
