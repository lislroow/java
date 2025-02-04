package spring.custom.common.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.exception.data.DataNotFoundException;
import spring.custom.common.exception.token.AccessTokenExpiredException;
import spring.custom.common.redis.RedisClient;
import spring.custom.common.syscode.ERROR;
import spring.custom.common.util.XffClientIpExtractor;
import spring.custom.dto.SysErrorLogDto;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  
  @Autowired ObjectMapper objectMapper;
  @Autowired RedisTemplate<String, String> redisTemplate;
  ListOperations<String, String> listOps;
  @Value("${spring.application.name}") String serviceName;
  String hostname = null;
  
  @PostConstruct
  public void init() {
    listOps = redisTemplate.opsForList();
    if (log.isInfoEnabled()) log.info("listOps: {}", listOps);
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      hostname = inetAddress.getHostName();
    } catch (UnknownHostException e) {
      log.error("{}", e.getStackTrace()[0]);
      hostname = "Unknown Host";
    }
  }
  
  @ExceptionHandler({DataNotFoundException.class})
  protected ResponseEntity<ProblemDetail> handleDataNotFoundException(DataNotFoundException e, WebRequest webRequest) {
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
  protected ResponseEntity<ProblemDetail> handleAccessTokenExpiredException(AccessTokenExpiredException e, WebRequest webRequest) {
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
  protected ResponseEntity<ProblemDetail> handleAppException(AppException e, WebRequest webRequest) {
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
  protected ResponseEntity<ProblemDetail> handleAuthorizationDeniedException(Exception e, WebRequest webRequest) {
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
  protected ResponseEntity<ProblemDetail> handleUncategorizedException(Exception e, WebRequest webRequest) {
    /* for debug */ if (log.isDebugEnabled()) log.error("", e);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    // issue: java.lang.IllegalStateException: getInputStream() has already been called for this request
    //StringBuilder requestBody = new StringBuilder();
    //try (BufferedReader reader = request.getReader()) {
    //  String line;
    //  while ((line = reader.readLine()) != null) {
    //    requestBody.append(line);
    //  }
    //} catch (IOException ie) {
    //  log.error("{}", ie.getStackTrace()[0]);
    //}
    
    SysErrorLogDto.RedisDto dto = SysErrorLogDto.RedisDto.builder()
        .txTime(LocalDateTime.now())
        .traceId(MDC.get("traceId"))
        .spanId(MDC.get("spanId"))
        .className(e.getClass().getSimpleName())
        .stacktrace(e.getStackTrace()[0] == null ? null : e.getStackTrace()[0].toString())
        .hostname(hostname)
        .hostIp(request.getLocalAddr())
        .serviceName(serviceName)
        .clientIp(XffClientIpExtractor.getClientIp(request))
        //.requestBody(requestBody.toString())
        .method(request.getMethod())
        .requestUri(request.getRequestURI())
        .requestParam(request.getQueryString())
        .createId(username)
        .createTime(LocalDateTime.now())
        .modifyId(username)
        .modifyTime(LocalDateTime.now())
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
