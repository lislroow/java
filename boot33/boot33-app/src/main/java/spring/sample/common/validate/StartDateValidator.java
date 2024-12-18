package spring.sample.common.validate;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartDateValidator implements ConstraintValidator<StartDate, LocalDateTime> {

  private static final LocalDateTime MIN_DATE = LocalDateTime.of(2000, 1, 1, 0, 0);

  @Override
  public boolean isValid(LocalDateTime startDate, ConstraintValidatorContext context) {
    if (startDate == null) {
      return true;
    }
    return !startDate.isBefore(MIN_DATE);
  }
  
}
