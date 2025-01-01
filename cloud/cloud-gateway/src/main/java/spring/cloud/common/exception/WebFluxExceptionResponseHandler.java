package spring.cloud.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.RESPONSE;
import spring.custom.common.exception.AppException;

@RestControllerAdvice
@Slf4j
public class WebFluxExceptionResponseHandler {

  static final String LOGFMT = "[{}] {}. {}";
  static final String CAUSE = "/ cause: ";
  
  @ExceptionHandler(AppException.class)
  @ResponseStatus(HttpStatus.OK)
  public Mono<ResponseDto<?>> handleAppException(AppException e) {
    ResponseDto<?> resDto = ResponseDto.body(e.getErrorCode(), e.getErrorMessage());
    /* for debug */ log.error(LOGFMT, e.getErrorCode(), e.getErrorMessage(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return Mono.just(resDto);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ResponseDto<?>> handleException(Exception e) {
    RESPONSE responseCode = RESPONSE.E999;
    ResponseDto<?> resDto = ResponseDto.body(RESPONSE.E999.code(), RESPONSE.E999.message());
    /* for debug */ log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return Mono.just(resDto);
  }
  
}
