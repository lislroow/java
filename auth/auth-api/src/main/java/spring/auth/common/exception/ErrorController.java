package spring.auth.common.exception;

import java.util.Arrays;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
  public ResponseDto<?> error(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    
    ERROR_CODE errorCode = Arrays.stream(ERROR_CODE.values())
        .filter(item -> item.code().equals("H"+response.getStatus()))
        .findFirst().orElse(ERROR_CODE.E999);
    String message = null;
    if (errorCode == ERROR_CODE.E999) {
      message = errorAttributes.get("error").toString();
    } else {
      message = errorCode.message();
    }
    return ResponseDto.body(errorCode.code(), message);
  }
}