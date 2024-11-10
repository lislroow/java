package spring.sample.config.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class CustomRestControllerAdvice {
  
  // sql 오류
  @ExceptionHandler(org.springframework.jdbc.BadSqlGrammarException.class)
  protected ResponseEntity<?> handleBadSqlGrammarException(BadSqlGrammarException e) {
    String str = String.format("sql 문에 오류가 있습니다.");
    ErrorResponse errorResponse = 
        ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, str)
        .build();
    System.err.println(e);
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
  // 그 밖에 모든 예외
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<?> handleException(Exception e) {
    ErrorResponse errorResponse = 
        ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
        .build();
    System.err.println(e);
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
}
