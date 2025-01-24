package spring.custom.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.exception.data.DataNotFoundException;
import spring.custom.common.exception.token.AccessTokenExpiredException;
import spring.custom.common.syscode.ERROR;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler({DataNotFoundException.class})
  protected ResponseEntity<ProblemDetail> handleDataNotFoundException(DataNotFoundException e, WebRequest request) {
    /* for debug */ if (log.isInfoEnabled()) log.info("data not found");
    
    HttpStatusCode status = HttpStatus.OK;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
        .title(e.getErrorCode())
        .detail(e.getErrorMessage())
        .status(status)
        .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
  @ExceptionHandler({AccessTokenExpiredException.class})
  protected ResponseEntity<ProblemDetail> handleAccessTokenExpiredException(AccessTokenExpiredException e, WebRequest request) {
    /* for debug */ if (log.isInfoEnabled()) log.info("accessToken expired");
    
    HttpStatusCode status = HttpStatus.UNAUTHORIZED;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
      .title(e.getErrorCode())
      .detail(e.getErrorMessage())
      .status(status)
      .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
  @ExceptionHandler({AppException.class})
  protected ResponseEntity<ProblemDetail> handleAppException(AppException e, WebRequest request) {
    /* for debug */ if (log.isDebugEnabled()) log.error("", e);
    
    HttpStatusCode status = null;
    ERROR errorCode = ERROR.fromCode(e.getErrorCode()).orElse(ERROR.E999);
    if (ERROR.isAuthError(errorCode)) {
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
  
  @ExceptionHandler({AuthorizationDeniedException.class})
  protected ResponseEntity<ProblemDetail> handleAuthorizationDeniedException(Exception e, WebRequest request) {
    /* for debug */ if (log.isInfoEnabled()) log.error("", e); 
    
    HttpStatusCode status = HttpStatus.FORBIDDEN;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
        .title(ERROR.A403.code())
        .detail(e.getMessage())
        .status(status)
        .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ProblemDetail> handleUncategorizedException(Exception e, WebRequest request) {
    /* for debug */ if (log.isInfoEnabled()) log.error("", e);
    
    HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
      .title(ERROR.E999.code())
      .detail(e.getCause() != null ? e.getCause().getMessage() : e.getMessage())
      .status(status)
      .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
}
