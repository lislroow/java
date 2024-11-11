package spring.sample.app.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import spring.sample.common.dto.ResponseDto;
import spring.sample.config.FeignConfig;

@FeignClient(name = "boot23-soapclient", configuration = {FeignConfig.class})
public interface FeignInternalSoapclientController {

  @GetMapping("/internal/soapclient/v1/{clientType}")
  public ResponseDto<Map<String, Object>> soap(@PathVariable String clientType);
}
