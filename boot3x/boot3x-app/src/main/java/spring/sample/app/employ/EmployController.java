package spring.sample.app.employ;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring.sample.app.employ.dto.EmployVO;

@RestController
@Slf4j
public class EmployController {
  
  private DiscoveryClient discoveryClient;
  
  public EmployController(DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
  }
  
  @GetMapping("/api/token")
  public String token(
      @RequestHeader(name = "x-token", required = false) String xToken,
      @RequestHeader(name = "x-user-id", required = false) String xUserId) {
    log.info("x-token={}", xToken);
    log.info("x-user-id={}", xUserId);
    return xToken;
  }
  
  @GetMapping("/api/discovery-clients")
  public List<String> getDiscoveryClients() {
    List<String> result = discoveryClient.getServices();
    log.info("services={}", result);
    return result;
  }
  
  @GetMapping("/api/employ/get-param")
  public EmployVO getParam(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @RequestParam("name") String name,
      @RequestParam("age") Integer age) {
    log.info("hKey={}", hValue);
    EmployVO result = EmployVO.builder()
        .name(name)
        .age(age)
        .build();
    return result;
  }
  
  @GetMapping("/api/employ/{id}/get-path-variable")
  public EmployVO getPathVariable(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @PathVariable("id") String id) {
    log.info("hKey={}", hValue);
    EmployVO result = EmployVO.builder()
        .id(id)
        .build();
    return result;
  }
  
  @GetMapping("/api/employ/get-attribute")
  public EmployVO getAttribute(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @ModelAttribute EmployVO param) {
    log.info("hKey={}", hValue);
    EmployVO result = param;
    return result;
  }
  
  @PostMapping("/api/employ/post-json")
  public EmployVO postJson(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @RequestBody EmployVO param) {
    log.info("hKey={}", hValue);
    EmployVO result = param;
    return result;
  }

  @GetMapping("/api/employ/get-time")
  public LocalDateTime getTime(
      @RequestHeader(name = "hKey", required = false) String hValue,
      @RequestParam("time") LocalDateTime param) {
    log.info("hKey={}", hValue);
    LocalDateTime result = param;
    return result;
  }
}
