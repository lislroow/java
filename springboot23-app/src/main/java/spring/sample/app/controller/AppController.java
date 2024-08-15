package spring.sample.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dto.DummyDTO;
import spring.sample.app.feign.EgressFeign;
import spring.sample.code.SOAP_CLIENT_TYPE;
import spring.sample.config.validator.EnumValidator;

@RestController
@Validated
@AllArgsConstructor
@Slf4j
public class AppController {
  
  private EgressFeign egressFeign;
  
  @GetMapping("/v1/app/dummy")
  public ResponseEntity<DummyDTO.DummyVO> dummy() {
    DummyDTO.DummyVO result = DummyDTO.DummyVO.builder()
        .id("123")
        .name("gildong.hong")
        .build();
    return ResponseEntity.ok(result);
  }
  
  @GetMapping("/v1/app/soap/{clientType}")
  public ResponseEntity<Map<String, Object>> soap(
      @PathVariable @EnumValidator(enumClazz = SOAP_CLIENT_TYPE.class) String clientType) {
    ResponseEntity<Map<String, Object>> result = egressFeign.soap(clientType);
    System.out.println(clientType);
    return result;
  }
  
}
