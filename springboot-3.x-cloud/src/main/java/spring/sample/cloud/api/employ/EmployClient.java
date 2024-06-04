package spring.sample.cloud.api.employ;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import spring.sample.cloud.api.employ.dto.EmployVO;

@FeignClient(name = "employClient", url = "${service.client.employ-url}")
public interface EmployClient {
  
  @GetMapping("/api/employ/create")
  ResponseEntity<EmployVO> create(
      @RequestHeader("apiKey") String apiKey,
      @RequestParam("name") String name,
      @RequestParam("age") Integer age);
  
}
