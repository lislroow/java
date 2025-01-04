package spring.cloud.common.exception;

import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.exception.AppException;

@RestControllerAdvice // @ControllerAdvice 일 경우 view resolver 를 찾게 됨
@Slf4j
public class ControllerExceptionHandlerWebFlux {

  static final String LOGFMT = "[{}] {}. {}";
  static final String CAUSE = "/ cause: ";
  
  @ExceptionHandler(AppException.class)
  @ResponseStatus(HttpStatus.OK)
  public Mono<ResponseDto<?>> handleAppException(AppException e) {
    ResponseDto<?> resDto = ResponseDto.body(e.getErrorCode(), e.getErrorMessage());
    /* for debug */ log.error(LOGFMT, e.getErrorCode(), e.getErrorMessage(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return Mono.just(resDto);
  }
  
  @ExceptionHandler(org.springframework.cloud.gateway.support.NotFoundException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ResponseDto<?>> handleNotFoundException(NotFoundException e) {
    ERROR_CODE responseCode = ERROR_CODE.G002;
    ResponseDto<?> resDto = ResponseDto.body(ERROR_CODE.G002.code(), ERROR_CODE.G002.message());
    /* for debug */ log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return Mono.just(resDto);
  }
  
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ResponseDto<?>> handleException(Exception e) {
    ERROR_CODE responseCode = ERROR_CODE.E999;
    ResponseDto<?> resDto = ResponseDto.body(ERROR_CODE.E999.code(), ERROR_CODE.E999.message());
    /* for debug */ log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return Mono.just(resDto);
  }
  
}
