package spring.sample.app.controller;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import spring.sample.app.enumcode.SOAP_CLIENT_TYPE;
import spring.sample.app.feign.FeignInternalSoapclientController;
import spring.sample.common.dto.ResponseDto;
import spring.sample.common.enumcode.RESPONSE_CODE;
import spring.sample.config.validator.EnumValidator;

@RestController
@Validated
@AllArgsConstructor
public class QualiController {
  
  private FeignInternalSoapclientController feignInternalSoapclientController;
  
  @PostMapping("/app/v1/quali/{clientType}")
  public ResponseDto<Map<String, Object>> soap(
      @PathVariable @EnumValidator(enumClazz = SOAP_CLIENT_TYPE.class) String clientType) {
    ResponseDto<Map<String, Object>> result = feignInternalSoapclientController.soap(clientType);
    Map<String, Object> body = null;
    
    if (RESPONSE_CODE.S000.name().equals(result.getHeader())) {
      body = result.getBody();
    }
    return ResponseDto.body(body);
  }
  
}
