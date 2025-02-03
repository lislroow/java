package spring.custom.common.exception;

import java.time.LocalDateTime;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.exception.data.DataNotFoundException;
import spring.custom.common.exception.token.AccessTokenExpiredException;
import spring.custom.common.redis.RedisClient;
import spring.custom.common.syscode.ERROR;
import spring.custom.dto.SysErrorLogDto;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  
  @Autowired ObjectMapper objectMapper;
  @Autowired RedisTemplate<String, String> redisTemplate;
  ListOperations<String, String> listOps;
  
  @PostConstruct
  public void init() {
    listOps = redisTemplate.opsForList();
    if (log.isInfoEnabled()) log.info("listOps: {}", listOps);
  }
  
  @ExceptionHandler({DataNotFoundException.class})
  protected ResponseEntity<ProblemDetail> handleDataNotFoundException(DataNotFoundException e, WebRequest request) {
    /* for debug */ if (log.isDebugEnabled()) log.info("data not found");
    
    HttpStatusCode status = HttpStatus.NO_CONTENT;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
        .title(e.getErrorCode())
        .detail(e.getErrorMessage())
        .status(status)
        .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
  @ExceptionHandler({AccessTokenExpiredException.class})
  protected ResponseEntity<ProblemDetail> handleAccessTokenExpiredException(AccessTokenExpiredException e, WebRequest request) {
    /* for debug */ if (log.isDebugEnabled()) log.info("accessToken expired");
    
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
    /* for debug */ if (log.isDebugEnabled()) log.error("", e); 
    
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
    /* for debug */ if (log.isDebugEnabled()) log.error("", e);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    SysErrorLogDto.RedisDto dto = SysErrorLogDto.RedisDto.builder()
        .txTime(LocalDateTime.now())
        .traceId(MDC.get("traceId"))
        .spanId(MDC.get("spanId"))
        .createId(username)
        .createTime(LocalDateTime.now())
        .modifyId(username)
        .createTime(LocalDateTime.now())
        .build();
    try {
      String value = objectMapper.writeValueAsString(dto);
      String key = RedisClient.LOG_KEY.ERROR_LOG.key();
      listOps.rightPush(key, value);
    } catch (JsonProcessingException e1) {
      log.error("{}", e1.getMessage());
    }
    
    HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemDetail problemDetail = ProblemDetailBuilder.builder()
      .title(ERROR.E999.code())
      .detail(e.getCause() != null ? e.getCause().getMessage() : e.getMessage())
      .status(status)
      .build();
    return ResponseEntity.status(status).body(problemDetail);
  }
  
}
