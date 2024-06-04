package spring.sample.cloud.config.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CustomValidErrorResponse {
  
  private final String code;
  private final String message;
  
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final List<ErrorField> errorFields;
  
  @Getter
  @Builder
  @RequiredArgsConstructor
  public static class ErrorField {
    private final String field;
    private final String message;
    
    public static ErrorField of(final org.springframework.validation.FieldError fieldError) {
      return ErrorField.builder()
          .field(fieldError.getField())
          .message(fieldError.getDefaultMessage())
          .build();
    }
    
    public static ErrorField of(final jakarta.validation.ConstraintViolation<?> constraintViolation) {
      jakarta.validation.Path path = constraintViolation.getPropertyPath();
      //String name = ((PathImpl)path).getLeafNode().getName();
      return ErrorField.builder()
          .field(path.toString())
          .message(constraintViolation.getMessage())
          .build();
    }
  }
}
