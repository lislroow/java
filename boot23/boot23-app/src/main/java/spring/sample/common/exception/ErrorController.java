package spring.sample.common.exception;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring.sample.common.dto.ResponseDto;
import spring.sample.common.enumcode.RESPONSE_CODE;

@RestController
@Slf4j
public class ErrorController extends AbstractErrorController {

  public ErrorController(ErrorAttributes errorAttributes) {
    super(errorAttributes);
  }
  
  @GetMapping(value = "/error", produces = "application/json;charset=UTF-8")
  public ResponseEntity<ResponseDto<Serializable>> error(HttpServletRequest request) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    HttpStatus status = getStatus(request);
    RESPONSE_CODE responseCode = RESPONSE_CODE.E999;
    errorAttributes.forEach((key, value) -> 
      log.error("[{}] {}. {}={}", responseCode.code(), responseCode.message(), key, value)
    );
    return ResponseEntity.status(status)
        .body(ResponseDto.body(responseCode));
  }
  
  // boot23 only 
  @Override
  public String getErrorPath() {
    return null;
  }
}
