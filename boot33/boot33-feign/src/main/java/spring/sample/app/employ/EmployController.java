package spring.sample.app.employ;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dto.EmployResDto;

@RestController
@Slf4j
public class EmployController {

  @GetMapping("/api/employ/get-param")
  public EmployResDto.Employ getParam(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @RequestParam("name") String name,
      @RequestParam("age") Integer age) {
    log.info("hKey={}", hValue);
    EmployResDto.Employ result = EmployResDto.Employ.builder()
        .name(name)
        .age(age)
        .build();
    return result;
  }
  
  @GetMapping("/api/employ/{id}/get-path-variable")
  public EmployResDto.Employ getPathVariable(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @PathVariable("id") String id) {
    log.info("hKey={}", hValue);
    EmployResDto.Employ result = EmployResDto.Employ.builder()
        .id(id)
        .build();
    return result;
  }
  
//  @GetMapping("/api/employ/get-attribute")
//  public EmployResDto.Employ getAttribute(
//      @RequestHeader(name = "hKey", required = false) String hValue,
//      @ModelAttribute EmployReqDto.Employ param) {
//    log.info("hKey={}", hValue);
//    Employ result = param;
//    return result;
//  }
  
//  @PostMapping("/api/employ/post-json")
//  public Employ postJson(
//      @RequestHeader(name = "hKey", required = false) String hValue,
//      @RequestBody Employ param) {
//    log.info("hKey={}", hValue);
//    Employ result = param;
//    return result;
//  }

  @GetMapping("/api/employ/get-time")
  public LocalDateTime getTime(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @RequestParam("time") LocalDateTime param) {
    log.info("hKey={}", hValue);
    LocalDateTime result = param;
    return result;
  }
}
