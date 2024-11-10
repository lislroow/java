package spring.sample.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.code.SOAP_CLIENT_TYPE;
import spring.sample.app.feign.FeignInternalEgressController;
import spring.sample.config.validator.EnumValidator;

@RestController
@Validated
@AllArgsConstructor
@Slf4j
public class QualiController {
  
  private FeignInternalEgressController feignInternalEgressController;
  
  @PostMapping("/app/v1/quali/{qualiId}")
  public ResponseEntity<Map<String, Object>> soap(
      @PathVariable @EnumValidator(enumClazz = SOAP_CLIENT_TYPE.class) String clientType) {
    ResponseEntity<Map<String, Object>> result = feignInternalEgressController.soap(clientType);
    return result;
  }
  
}
