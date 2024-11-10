package spring.sample.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.sample.security.Role;
import spring.sample.security.User;

public class AuthenticationFilter extends GenericFilterBean {
  
  private SecurityContextHolderStrategy securityContextHolderStrategy = 
      SecurityContextHolder.getContextHolderStrategy();
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String xUserId = ((HttpServletRequest) request).getHeader("x-user-id");
    if (ObjectUtils.isEmpty(xUserId)) {
      HttpServletResponse res = ((HttpServletResponse) response);
      res.setStatus(HttpStatus.FORBIDDEN.value());
      
      MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
      MediaType jsonMimeType = MediaType.APPLICATION_JSON;
      Map<String, String> message = Map.of("result", "잘못된 접근 입니다.");
      if (converter.canWrite(message.getClass(), jsonMimeType)) {
        converter.write(message, jsonMimeType, new ServletServerHttpResponse(res));
      }
      return;
    }
    
    User user = new User();
    user.setId(xUserId);
    user.setRole(Role.ROLE_USER);
    
    Authentication authentication = null;
    authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
    context.setAuthentication(authentication);
    this.securityContextHolderStrategy.setContext(context);
    
    chain.doFilter(request, response);
  }
}
