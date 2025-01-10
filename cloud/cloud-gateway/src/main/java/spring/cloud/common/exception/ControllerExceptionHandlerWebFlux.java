package spring.cloud.common.exception;

import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import spring.custom.common.enumcode.Error;
import spring.custom.common.exception.AppException;
import spring.custom.common.exception.ProblemDetailBuilder;

@RestControllerAdvice // @ControllerAdvice 일 경우 view resolver 를 찾게 됨
@Slf4j
public class ControllerExceptionHandlerWebFlux {

  static final String LOGFMT = "[{}] {}. {}";
  static final String CAUSE = "/ cause: ";
  
  @ExceptionHandler({AppException.class})
  public Mono<ResponseEntity<ProblemDetail>> handleAppException(AppException e) {
    /* for debug */ if (log.isDebugEnabled()) log.error("", e);
    
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
      .title(e.getErrorCode())
      .detail(e.getErrorMessage())
      .status(status)
      .build();
    return Mono.just(ResponseEntity.status(status).body(problemDetail));
  }
  
  @ExceptionHandler(org.springframework.cloud.gateway.support.NotFoundException.class)
  public Mono<ResponseEntity<ProblemDetail>> handleNotFoundException(NotFoundException e) {
    /* for debug */ if (log.isInfoEnabled()) log.error("", e);
    
    HttpStatusCode status = e.getStatusCode(); // 503: SERVICE_UNAVAILABLE
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
        .title(Error.E503.code())
        .detail(e.getMessage())
        .status(status)
        .build();
    return Mono.just(ResponseEntity.status(status).body(problemDetail));
  }
  
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ResponseEntity<ProblemDetail>> handleUncategorizedException(Exception e) {
    boolean isKnown = e instanceof NoResourceFoundException;
    /* for debug */ if (log.isInfoEnabled()) {
      if (isKnown) log.error("{}", e.getMessage());
      else log.error("", e);
    }
    
    HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
      .title(Error.E999.code())
      .detail(e.getMessage())
      .status(status)
      .build();
    return Mono.just(ResponseEntity.status(status).body(problemDetail));
  }
  
}
