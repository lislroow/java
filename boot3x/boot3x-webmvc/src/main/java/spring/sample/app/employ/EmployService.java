package spring.sample.app.employ;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.validation.method.ParameterValidationResult;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.employ.dto.EmployRegREQ;

@Service
@Validated
@Slf4j
public class EmployService {
  
  // class-level 에 '@Validated' 선언 필요
  // parameter-level 에 '@Valid' 선언 필요 
  public EmployRegREQ validByAOP(@Valid EmployRegREQ param) {
    log.info("param = {}", param);
    return param;
  }
  
  // 'jakarta.validation.Validator' bean 을 사용한 validate
  @Autowired
  private jakarta.validation.Validator jakartaValidator;
  public EmployRegREQ validByInvokeWithJakarta(EmployRegREQ param) {
    log.info("param = {}", param);
    jakartaValidator.validate(param);
    Set<ConstraintViolation<EmployRegREQ>> violations = jakartaValidator.validate(param);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
    return param;
  }
  
  // 'org.springframework.validation.Validator' bean 을 사용한 validate
  // Errors 를 포함시킬 적절한 exception 타입이 없으므로 custom-exception 을 생성해야 함 
  // preprocess 클래스인 PayloadMethodArgumentResolver 에서 throw 하는
  //   MethodValidationException 예외 타입은 적합하지 않음
  @Autowired
  private org.springframework.validation.Validator springValidator;
  public EmployRegREQ validByInvokeWithSpring(EmployRegREQ param) throws Exception {
    log.info("param = {}", param);
    org.springframework.validation.Errors errors = 
        new org.springframework.validation.BeanPropertyBindingResult(param, "EmployRegREQ");
    springValidator.validate(param, errors);
    if (errors.hasErrors()) {
      //log.error("validation error: {}", errors.toString());
      //ParameterErrors paramErrors = new ParameterErrors(parameter, argument, errors, null, null, null);
      //List<ParameterValidationResult> results = Collections.singletonList(paramErrors);
      //throw new MethodValidationException(MethodValidationResult.create(target, method, results));
      throw new Exception(errors.toString());
    }
    return param;
  }
}
