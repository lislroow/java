package spring.custom.common.exception;

import java.io.Serializable;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

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
  public ResponseEntity<ResponseDto<Serializable>> error() {
    //Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    //HttpStatus status = getStatus(request);
    ERROR_CODE responseCode = ERROR_CODE.E999;
    //errorAttributes.forEach((key, value) -> 
    //  log.error("[{}] {}. {}={}", responseCode.code(), responseCode.message(), key, value)
    //);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseDto.body(responseCode));
  }

  //@Override
  public String getErrorPath() {
    return "/error";
  }
}
