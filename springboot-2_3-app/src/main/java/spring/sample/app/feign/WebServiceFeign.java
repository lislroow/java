package spring.sample.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import spring.sample.app.feign.dto.WebServiceDTO;

@FeignClient(name = "externalFeignClient", url = "http://localhost:8080")
public interface WebServiceFeign {

  @GetMapping("/api/v1/external")
  public ResponseEntity<WebServiceDTO.MemberVO> getExternalInfo();
  
}
