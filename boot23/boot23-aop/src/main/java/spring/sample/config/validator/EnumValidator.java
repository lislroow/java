package spring.sample.config.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {
  private boolean required;
  private List<String> valueList;

  @Override
  public synchronized boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value instanceof String) {
      if (!required && StringUtils.isBlank((String)value)) {
        return true;
      }
    } else {
      return false;
    }
    return valueList.contains(value);
  }

  @Override
  @SuppressWarnings("rawtypes")
  public synchronized void initialize(EnumValid constraintAnnotation) {
    required = constraintAnnotation.required();
    valueList = new ArrayList<>();
    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
    Class<?> clz = constraintAnnotation.enumClazz();

    try {
      Method method = clz.getMethod(constraintAnnotation.compareValue());
      Object[] objects = clz.getEnumConstants();
      if (objects != null && objects.length > 0) {
        for(Object obj : objects) {
          valueList.add((String)method.invoke(obj));
        }
      }
    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      Enum[] enumValArr = enumClass.getEnumConstants();
      for (Enum enumVal : enumValArr) {
        valueList.add(enumVal.toString());
      }
    }
  }
}
