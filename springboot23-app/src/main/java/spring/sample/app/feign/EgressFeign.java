package spring.sample.app.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import spring.sample.config.FeignConfig;

@FeignClient(name = "springboot23-egress", configuration = {FeignConfig.class})
public interface EgressFeign {

  @GetMapping("/v1/internal/egress/soap/{clientType}")
  public ResponseEntity<Map<String, Object>> soap(
      @PathVariable(name = "clientType") String clientType);
}
