package spring.sample.webmvc.api.employ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import spring.sample.webmvc.api.employ.dto.EmployPageREQ;
import spring.sample.webmvc.api.employ.dto.EmployRegREQ;
import spring.sample.webmvc.api.employ.dto.EmployVO;

@RestController
public class EmployController {

/*
curl -L 'http://localhost:8080/api/validate/forModelAttribute?page=1&pageSize=5&name=lee'
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
curl -L 'http://localhost:8080/api/validate/forRequestBody' \
--header 'Content-Type: application/json' \
--data '{
    "name": "gdhong"
}'
*/
  @PostMapping("/api/validate/forRequestBody")
  public EmployVO forRequestBody(
      @Valid @RequestBody EmployRegREQ param) {
    EmployVO vo = EmployVO.builder()
        .id(param.getId())
        .name(param.getName())
        .age(param.getAge())
        .eyesight(param.getEyesight())
        .password(param.getPassword())
        .confirmPassword(param.getConfirmPassword())
        .employCode(param.getEmployCode())
        .roleId(param.getRoleId())
        .allowedIp(param.getAllowedIp())
        .startDate(param.getStartDate())
        .build();
    return vo;
  }
}
