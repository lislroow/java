package spring.custom.common.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.RequestDispatcher;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.syscode.ERROR;

@Component
@Slf4j
public class ErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
    //server.error.include-stacktrace=always
    //server.error.include-message=always
    //server.error.include-binding-errors=always
    // options.getIncludes();
    // 전체: STATUS, ERROR, PATH, EXCEPTION, STACK_TRACE, MESSAGE, BINDING_ERRORS
    // 기본: STATUS, ERROR, PATH
    
    Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, options);
    
    Map<String, Object> problemDetails = new LinkedHashMap<>();
    problemDetails.put("type", "/error");
    problemDetails.put("status", defaultErrorAttributes.getOrDefault("status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    problemDetails.put("title", defaultErrorAttributes.getOrDefault("status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    problemDetails.put("detail", defaultErrorAttributes.getOrDefault("message", "An unexpected error occurred."));
    problemDetails.put("instance", defaultErrorAttributes.getOrDefault("instance", webRequest.getDescription(false)));
    problemDetails.put("timestamp", LocalDateTime.now());
    
    /* for debug */ if (log.isInfoEnabled()) {
      String trace = defaultErrorAttributes.getOrDefault("trace", "").toString();
      String first = trace.split("\n")[0];
      log.error(first);
    }
    /* for debug */ if (log.isDebugEnabled()) {
      java.util.Arrays.asList(webRequest.getAttributeNames(org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST)).stream()
        .forEach(item -> System.out.println("["+item+"]: "+webRequest.getAttribute(item, org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST)));
    }
    
    /* custom : filter 에서 response.sendError(int sc, String msg) 로 예외 처리 대응 */
    Throwable t = (Throwable) webRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION, RequestAttributes.SCOPE_REQUEST);
    if (t instanceof AppException) {
      AppException appException = (AppException) t;
      problemDetails.put("title", appException.getErrorCode());
      problemDetails.put("detail", appException.getErrorMessage());
    } else if (t instanceof BadCredentialsException) {
      problemDetails.put("title", ERROR.A017.code());
      problemDetails.put("detail", ERROR.A017.message());
    } else if (t instanceof DisabledException) {
      problemDetails.put("title", ERROR.A010.code());
      problemDetails.put("detail", ERROR.A010.message());
    } else if (t instanceof LockedException) {
      problemDetails.put("title", ERROR.A011.code());
      problemDetails.put("detail", ERROR.A011.message());
    } else if (t instanceof CredentialsExpiredException) {
      problemDetails.put("title", ERROR.A012.code());
      problemDetails.put("detail", ERROR.A012.message());
    }
    
    Object exception = defaultErrorAttributes.get("exception");
    if (exception != null) {
      problemDetails.put("exception", exception.toString());
    }
    
    return problemDetails;
  }
  
}
