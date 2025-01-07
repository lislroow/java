package spring.custom.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.enumcode.ERROR_CODE;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler({AppException.class})
  protected ResponseEntity<ProblemDetail> handleAppException(AppException e, WebRequest request) {
    /* for debug */ if (log.isDebugEnabled()) log.error("{}", e);
    
    HttpStatusCode status = null;
    ERROR_CODE errorCode = ERROR_CODE.fromCode(e.getErrorCode()).orElse(ERROR_CODE.E999);
    
    if (ERROR_CODE.isAuthError(errorCode)) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    } else {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
      .title(e.getErrorCode())
      .detail(e.getErrorMessage())
      .status(status)
      .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ProblemDetail> handleUncategorizedException(Exception e, WebRequest request) {
    /* for debug */ if (log.isDebugEnabled()) log.error("{}", e); 
    
    HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
      .title(ERROR_CODE.E999.code())
      .detail(e.getMessage())
      .status(status)
      .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
}
