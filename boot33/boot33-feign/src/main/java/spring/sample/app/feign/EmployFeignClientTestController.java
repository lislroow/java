package spring.sample.app.feign;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dto.EmployReqDto;
import spring.sample.app.dto.EmployResDto;

@RestController
@Slf4j
public class EmployFeignClientTestController {

  @Autowired
  EmployFeignClient client;
  
/*
curl -sX GET http://localhost:8888/api/employ/get-param/test
*/
  @GetMapping("/api/employ/get-param/test")
  public ResponseEntity<EmployResDto.Employ> getParam(
      @RequestParam("name") String name,
      @RequestParam("age") Integer age) {
    ResponseEntity<EmployResDto.Employ> result = client.getParam(name, age);
    return result;
  }
  
/*
curl -sX GET http://localhost:8888/api/employ/get-param/test
*/
  @GetMapping("/api/employ/{id}/get-path-variable/test")
  public ResponseEntity<EmployResDto.Employ> getPathVariable(
      @PathVariable("id") String id) {
    ResponseEntity<EmployResDto.Employ> result = client.getPathVariable(id);
    return result;
  }
  
/*
curl -sX GET http://localhost:8888/api/employ/get-attribute/test
*/
//  @GetMapping("/api/employ/get-attribute/test")
//  public ResponseEntity<EmployResDto.Employ> getPathVariable(
//      @ModelAttribute EmployReqDto.Employ param) {
//    ResponseEntity<EmployResDto.Employ> result = client.getAttribute(param);
//    return result;
//  }
  
/*
curl -sX POST http://localhost:8888/api/employ/post-json/test
*/
//  @PostMapping("/api/employ/post-json/test")
//  public ResponseEntity<EmployResDto.Employ> postJson(
//      @RequestBody EmployReqDto.Employ param) {
//    ResponseEntity<EmployResDto.Employ> result = client.postJson(param);
//    return result;
//  }
  
  @GetMapping("/api/employ/get-time/test")
  public ResponseEntity<LocalDateTime> getTime(
      @RequestParam("time") LocalDateTime param) {
    ResponseEntity<LocalDateTime> result = client.getTime(param);
    return result;
  }
}
