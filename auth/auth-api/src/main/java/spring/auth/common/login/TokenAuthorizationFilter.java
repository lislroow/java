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

@Slf4j
@RequiredArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter {
  
  final TokenService tokenService;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorization != null && authorization.startsWith("Bearer ")) {
      String tokenId = authorization.substring(7);
      
      /* for debug */ if (log.isDebugEnabled()) log.debug("tokenId: {}", tokenId);
      try {
        String tokenValue = tokenService.verifyTokenId(tokenId);
        AuthorizationRequestWrapper requestWrapper = new AuthorizationRequestWrapper("Bearer " + tokenValue, request);
        filterChain.doFilter(requestWrapper, response);
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
  
  private class AuthorizationRequestWrapper extends HttpServletRequestWrapper {
    private final String authorization;
    
    public AuthorizationRequestWrapper(String authorization, HttpServletRequest request) {
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
