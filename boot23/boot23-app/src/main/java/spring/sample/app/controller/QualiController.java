package spring.sample.app.controller;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import spring.sample.app.enumcode.SOAP_CLIENT_TYPE;
import spring.sample.app.feign.InternalSoapclientFeign;
import spring.sample.common.constant.Constant;
import spring.sample.common.dto.ResponseDto;
import spring.sample.common.enumcode.RESPONSE_CODE;
import spring.sample.common.validator.EnumValidator;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping(value = Constant.APP.BASE_URI)
public class QualiController {
  
  private InternalSoapclientFeign internalSoapclientFeign;
  
  @PostMapping("/v1/quali/{clientType}")
  public ResponseDto<Map<String, Object>> soap(
      @PathVariable @EnumValidator(enumClazz = SOAP_CLIENT_TYPE.class) String clientType) {
    ResponseDto<Map<String, Object>> result = internalSoapclientFeign.soap(clientType);
    Map<String, Object> body = null;
    
    if (RESPONSE_CODE.S000.name().equals(result.getHeader())) {
      body = result.getBody();
    }
    return ResponseDto.body(body);
  }
  
}
