package spring.sample.app.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import spring.sample.app.dto.QualificationResDto;
import spring.sample.config.FeignConfig;

@FeignClient(name = "boot23-app", configuration = {FeignConfig.class})
public interface FeignQualificationController {

  @PostMapping("/v1/internal/qualification/verify-using-webservice")
  public ResponseEntity<QualificationResDto.SoapRes> verifyUsingWebservice();
  
  @PostMapping("/v1/internal/qualification/verify-using-httpclient")
  public ResponseEntity<Map<String, Object>> verifyUsingHttpclient();
}
