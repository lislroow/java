package spring.sample.config.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@ReportAsSingleViolation
public @interface EnumValidator {
  String message() default "Value is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends Enum<?>> enumClazz();

  boolean required() default true;
  String compareValue() default "code";
}