package spring.sample.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import spring.sample.app.feign.dto.WebServiceDTO;
import spring.sample.code.SOAP_CLIENT_TYPE;
import spring.sample.config.FeignConfig;

//@FeignClient(name = "springboot23-egress", url = "/", configuration = {FeignConfig.class})
@FeignClient(name = "springboot23-egress", configuration = {FeignConfig.class})
//@FeignClient(name = "springboot23-egress")
public interface EgressFeign {

  @GetMapping("/v1/internal/egress/soap/{clientType}")
  public ResponseEntity<WebServiceDTO.MemberVO> soap(
      @PathVariable(name = "clientType") String clientType);
}
