package spring.custom.common.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.ERROR_CODE;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  
  static final String LOGFMT = "[{}] {}. {}";
  static final String CAUSE = "/ cause: ";
  
  @ExceptionHandler({AppException.class})
  @ResponseStatus(HttpStatus.OK)
  protected ResponseDto<Serializable> handleMarketException(AppException e, WebRequest request) {
    /* for debug */ log.error(LOGFMT, e.getErrorCode(), e.getErrorMessage(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return ResponseDto.body(e.getErrorCode(), e.getErrorMessage());
  }
  
  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseDto<Serializable> handleException(Exception e) {
    ERROR_CODE responseCode = ERROR_CODE.E999;
    /* for debug */ log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return ResponseDto.body(responseCode.code(), responseCode.message());
  }
  
}
