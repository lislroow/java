package spring.sample.common.exception;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.ERROR_CODE;

@RestController
@Slf4j
public class ErrorController extends AbstractErrorController {

  public ErrorController(ErrorAttributes errorAttributes) {
    super(errorAttributes);
  }
  
  @GetMapping(value = "/error", produces = "application/json;charset=UTF-8")
  public ResponseDto<Serializable> error() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    errorAttributes.forEach((key, value) -> 
      log.error("[{}] {}. {}={}", key, value)
    );
    ERROR_CODE responseCode = ERROR_CODE.E999;
    return ResponseDto.body(responseCode.code(), responseCode.message());
  }
  
  //@Override
  public String getErrorPath() {
    return "/error";
  }
}
