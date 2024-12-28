package spring.custom.common.validator;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
