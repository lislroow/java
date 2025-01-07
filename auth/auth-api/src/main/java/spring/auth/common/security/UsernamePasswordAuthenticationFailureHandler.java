package spring.auth.common.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {
  
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
    request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception);
    request.setAttribute(RequestDispatcher.ERROR_MESSAGE, exception.getMessage());
    response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    
    /*
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    MediaType jsonMimeType = MediaType.APPLICATION_JSON;
    Map<String, String> message = Map.of("result", "사용자 정보가 일치하지 않습니다.");
    if (converter.canWrite(message.getClass(), jsonMimeType)) {
        converter.write(message, jsonMimeType, new ServletServerHttpResponse(response));
    }
    */
  }
  
}
