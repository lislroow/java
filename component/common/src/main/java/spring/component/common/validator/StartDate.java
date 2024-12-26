package spring.component.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = StartDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartDate {
  
  String message() default "startDate 는 반드시 2000-01-01 이후가 되어야 합니다.";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
  
}