package spring.sample.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "externalFeignClient", url = "http://localhost:8080")
public interface ExternalFeignClient {

  @GetMapping("/api/v1/external")
  public ResponseEntity<ExternalDto.ExternalInfoVo> getExternalInfo();
  
}
