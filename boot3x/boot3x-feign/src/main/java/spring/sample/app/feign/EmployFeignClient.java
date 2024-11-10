package spring.sample.app.feign;

import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import spring.sample.app.dto.EmployResDto;

@FeignClient(name = "employFeignClient", url = "${service.client.employ-url}")
public interface EmployFeignClient {
  
  @GetMapping("/api/employ/get-param")
  ResponseEntity<EmployResDto.Employ> getParam(
      @RequestParam("name") String name,
      @RequestParam("age") Integer age);
  
  @GetMapping("/api/employ/{id}/get-path-variable")
  ResponseEntity<EmployResDto.Employ> getPathVariable(
      @PathVariable("id") String id);
  
//  @GetMapping("/api/employ/get-attribute")
//  ResponseEntity<EmployResDto.Employ> getAttribute(
//      //@ModelAttribute EmployVO param); // POST 방식으로 변경되는 issue 발생
//      @SpringQueryMap EmployReqDto.Employ param);
  
//  @PostMapping("/api/employ/post-json")
//  ResponseEntity<EmployReqDto.Employ> postJson(
//      @RequestBody EmployReqDto.Employ param);
  
  @GetMapping("/api/employ/get-time")
  ResponseEntity<LocalDateTime> getTime(
      @RequestParam("time") LocalDateTime param);
  
}
