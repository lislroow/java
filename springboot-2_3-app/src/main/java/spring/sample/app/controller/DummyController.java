package spring.sample.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dto.DummyDTO;
import spring.sample.app.feign.WebServiceFeign;
import spring.sample.app.feign.dto.WebServiceDTO;

@RestController
@AllArgsConstructor
@Slf4j
public class DummyController {
  
  private WebServiceFeign webServiceFeign;
  
  @GetMapping("/v1/app/dummy")
  public ResponseEntity<DummyDTO.DummyVO> dummy() {
    DummyDTO.DummyVO result = DummyDTO.DummyVO.builder()
        .id("123")
        .name("gildong.hong")
        .build();
    return ResponseEntity.ok(result);
  }
  
  @GetMapping("/v1/app/webservice")
  public ResponseEntity<WebServiceDTO.MemberVO> getExternalInfo() {
    ResponseEntity<WebServiceDTO.MemberVO> result = webServiceFeign.getExternalInfo();
    return result;
  }
  
}
