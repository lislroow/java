package spring.sample.cloud.api.employ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring.sample.cloud.api.employ.dto.EmployVO;

@RestController
@Slf4j
public class EmployController {

  @GetMapping("/api/employ/get-param")
  public EmployVO getParam(
      @RequestParam("name") String name,
      @RequestParam("age") Integer age) {
    EmployVO result = EmployVO.builder()
        .name(name)
        .age(age)
        .build();
    return result;
  }
  
  @GetMapping("/api/employ/{id}/get-path-variable")
  public EmployVO getPathVariable(
      @PathVariable("id") String id) {
    EmployVO result = EmployVO.builder()
        .id(id)
        .build();
    return result;
  }
  
  @GetMapping("/api/employ/get-attribute")
  public EmployVO getAttribute(@ModelAttribute EmployVO param) {
    EmployVO result = param;
    return result;
  }
  
  @PostMapping("/api/employ/post-json")
  public EmployVO postJson(@RequestBody EmployVO param) {
    EmployVO result = param;
    return result;
  }
  
}
