package spring.auth.common.login;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;
import spring.custom.common.util.IdGenerator;
import spring.custom.common.util.XffClientIpExtractor;
import spring.custom.dto.TokenDto;

@Slf4j
@RequiredArgsConstructor
public class TokenVerifyFilter extends OncePerRequestFilter {
  
  final TokenService tokenService;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorization != null && authorization.startsWith("Bearer ")) {
      String tokenId = authorization.substring(7);
      /* for debug */ if (log.isDebugEnabled()) log.debug("tokenId: {}", tokenId);
      
      String clientIp = XffClientIpExtractor.getClientIp(request);
      // String userAgent = request.getHeaders().get(Constant.HTTP_HEADER.USER_AGENT).get(0);
      String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
      String clientIdent = IdGenerator.createClientIdent(clientIp, userAgent);
      try {
        TokenDto.VerifyRes result = tokenService.verifyToken(tokenId, clientIdent);
        filterChain.doFilter(
            new TokenVerifyHttpServletRequest("Bearer " + result.getAccessToken(), request), response);
      } catch (Exception exception) {
        request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
        request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception);
        request.setAttribute(RequestDispatcher.ERROR_MESSAGE, exception.getMessage());
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.sendError(status.value(), status.getReasonPhrase());
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }
  
  private class TokenVerifyHttpServletRequest extends HttpServletRequestWrapper {
    
    private final String authorization;
    
    public TokenVerifyHttpServletRequest(String authorization, HttpServletRequest request) {
      super(request);
      this.authorization = authorization;
    }
    
    @Override
    public String getHeader(String name) {
      if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)) {
        return this.authorization;
      }
      return super.getHeader(name);
    }
  }

}
