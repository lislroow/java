package spring.sample.cloud.api.feign;

import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import spring.sample.cloud.api.employ.dto.EmployVO;

@FeignClient(name = "employFeignClient", url = "${service.client.employ-url}")
public interface EmployFeignClient {
  
  @GetMapping("/api/employ/get-param")
  ResponseEntity<EmployVO> getParam(
      @RequestParam("name") String name,
      @RequestParam("age") Integer age);
  
  @GetMapping("/api/employ/{id}/get-path-variable")
  ResponseEntity<EmployVO> getPathVariable(
      @PathVariable("id") String id);
  
  @GetMapping("/api/employ/get-attribute")
  ResponseEntity<EmployVO> getAttribute(
      //@ModelAttribute EmployVO param); // POST 방식으로 변경되는 issue 발생
      @SpringQueryMap EmployVO param);
  
  @PostMapping("/api/employ/post-json")
  ResponseEntity<EmployVO> postJson(
      @RequestBody EmployVO param);
  
  @GetMapping("/api/employ/get-time")
  ResponseEntity<LocalDateTime> getTime(
      @RequestParam("time") LocalDateTime param);
  
}
