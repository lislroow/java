package spring.sample.config.exception;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import spring.sample.config.exception.CustomValidErrorResponse.ErrorField;

@RestControllerAdvice
@Order(1)
@Slf4j
public class CustomRestControllerAdvice {
  
  @ExceptionHandler(ArithmeticException.class)
  protected ResponseEntity<?> handleArithmeticException(ArithmeticException e) {
    ErrorResponse errorResponse = 
        // e.g) e.getMessage(): "정수는 0으로 나눌 수 없습니다."
        ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
        .build();
    log.error("", e);
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
  // @RequestParam, @PathVariable 에 대한 validation 오류가 발생했을 때는
  //   HandlerMethodValidationException 타입의 오류가 발생합니다.
  //   e.g) @RequestParam(name = "number") @Min(value = 0) Integer number
  // @Valid 를 사용해야할 경우, @RequestBody, @ModelAttribute 을 선언해주세요.
  @ExceptionHandler(HandlerMethodValidationException.class)
  protected ResponseEntity<?> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
    System.out.println("@RequestParam, @PathVariable 일 경우 예외 처리되는 메소드입니다.");
    ErrorResponse errorResponse = 
        ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
        .build();
    log.error("", e);
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
  // 그 밖에 모든 예외
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<?> handleException(Exception e) {
    ErrorResponse errorResponse = 
        ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
        .build();
    log.error("", e);
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> listFieldError = e.getFieldErrors();
    List<ErrorField> errorFields = listFieldError.stream()
        .map(fieldError -> ErrorField.of(fieldError))
        .collect(Collectors.toList());
    CustomValidErrorResponse errorResponse = CustomValidErrorResponse.builder()
        .code("validation_error_code")
        .message("validation_error_message")
        .errorFields(errorFields)
        .build();
    log.error("", e);
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> set = e.getConstraintViolations();
    List<ErrorField> errorFields = set.stream()
        .map(constraintViolation -> ErrorField.of(constraintViolation))
        .collect(Collectors.toList());
    CustomValidErrorResponse errorResponse = CustomValidErrorResponse.builder()
        .code("constraint_error_code")
        .message("constraint_error_message")
        .errorFields(errorFields)
        .build();
    log.error("", e);
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
}
