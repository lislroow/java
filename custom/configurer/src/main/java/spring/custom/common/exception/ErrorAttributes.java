package spring.custom.common.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

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
    
    Object exception = defaultErrorAttributes.get("exception");
    if (exception != null) {
      problemDetails.put("exception", exception.toString());
    }
    
    return problemDetails;
  }
  
}
