package spring.sample.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import spring.sample.app.code.SOAP_CLIENT_TYPE;
import spring.sample.app.feign.dto.WebServiceDTO;

@FeignClient(name = "springboot-2_3-egress", url = "/")
public interface EgressFeign {

  @GetMapping("/v1/internal/egress/soap/{soapClientType}")
  public ResponseEntity<WebServiceDTO.MemberVO> soap(
      @PathVariable(name = "soapClientType") SOAP_CLIENT_TYPE soapClientType);
  
}
