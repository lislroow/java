package spring.sample.app.employ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.employ.dto.EmployPageREQ;
import spring.sample.app.employ.dto.EmployRegREQ;
import spring.sample.app.employ.dto.EmployVO;

@RestController
@Slf4j
public class EmployController {

/*
curl -L 'http://localhost:8888/api/validate/forModelAttribute?page=1&pageSize=5&name=lee'
*/
  @GetMapping("/api/validate/forModelAttribute")
  public EmployVO forModelAttribute(
      @Valid @ModelAttribute EmployPageREQ param) {
    EmployVO vo = EmployVO.builder()
        .id(param.getId())
        .name(param.getName())
        .build();
    return vo;
  }
  
/*
curl -L 'http://localhost:8888/api/validate/forRequestBody' \
--header 'Content-Type: application/json' \
--data '{
    "name": "gdhong"
}'
*/
  @PostMapping("/api/validate/forRequestBody")
  public EmployVO forRequestBody(
      @Valid @RequestBody EmployRegREQ param) {
    return EmployVO.of(param);
  }

/*
curl -L 'http://localhost:8888/api/validate/forRequestParam?number=0'
*/
  @GetMapping("/api/validate/forRequestParam")
  public Integer forRequestParam(
      @RequestParam(name = "number") @Min(value = 0) Integer number) {
    Integer result = null;
    try {
      result = 10 / number;
    } catch (ArithmeticException e) {
      throw e;
    }
    return result;
  }
  
  @GetMapping("/api/validate/{number}/forPathVariable")
  public Integer forPathVariable(
      @PathVariable(name = "number") @Min(value = 0) Integer number) {
    Integer result = null;
    try {
      result = 10 / number;
    } catch (ArithmeticException e) {
      throw e;
    }
    return result;
  }
  
  @Autowired
  private EmployService service;
  
  @PostMapping("/api/validate/onServiceByAOP")
  public EmployVO onServiceByAOP(@RequestBody EmployRegREQ param) {
    log.info("param = {}", param);
    return EmployVO.of(service.validByAOP(param));
  }
  
  @PostMapping("/api/validate/onServiceByInvokeWithJakarta")
  public EmployVO onServiceByInvokeWithJakarta(@RequestBody EmployRegREQ param) {
    log.info("param = {}", param);
    return EmployVO.of(service.validByInvokeWithJakarta(param));
  }
  
  @PostMapping("/api/validate/onServiceByInvokeWithSpring")
  public EmployVO onServiceByInvokeWithSpring(@RequestBody EmployRegREQ param) throws Exception {
    log.info("param = {}", param);
    return EmployVO.of(service.validByInvokeWithSpring(param));
  }
}
