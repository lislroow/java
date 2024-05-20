package spring.sample.webmvc.config.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import spring.sample.webmvc.config.exception.CustomErrorResponse.ErrorField;

@RestControllerAdvice
@Order(1)
public class CustomRestControllerAdvice {
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<?> handleIllegalArgumentException(MethodArgumentNotValidException e) {
    List<FieldError> listFieldError = e.getFieldErrors();
    List<ErrorField> errorFields = listFieldError.stream()
        .map(fieldError -> ErrorField.of(fieldError))
        .collect(Collectors.toList());
    CustomErrorResponse errorResponse = CustomErrorResponse.builder()
        .code("validation_error_code")
        .message("validation_error_message")
        .errorFields(errorFields)
        .build();
    return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
  }
  
}
