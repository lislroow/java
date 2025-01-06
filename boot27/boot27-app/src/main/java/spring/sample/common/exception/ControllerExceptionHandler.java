package spring.sample.common.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ControllerExceptionHandler extends AbstractErrorController {

  public ControllerExceptionHandler(ErrorAttributes errorAttributes) {
    super(errorAttributes);
  }
  
  @GetMapping(value = "/error", produces = "application/json;charset=UTF-8")
  public ResponseEntity<Map<String, Object>> error(Exception e) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    errorAttributes.forEach((key, value) -> 
      log.error("[{}] {}. {}={}", key, value)
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(errorAttributes);
  }
  
  //@Override
  public String getErrorPath() {
    return "/error";
  }
}
