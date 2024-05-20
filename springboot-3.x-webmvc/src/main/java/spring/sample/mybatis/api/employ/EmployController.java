package spring.sample.mybatis.api.employ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.sample.mybatis.api.employ.dto.EmployPageREQ;
import spring.sample.mybatis.api.employ.dto.EmployVO;

@RestController
public class EmployController {

  /*
    curl --location 'http://localhost:8080/api/employ/listByName?page=1&pageSize=5&name=lee'
   */
  @GetMapping("/api/employ/listByName")
  public EmployVO selectListByName(EmployPageREQ param) {
    EmployVO vo = new EmployVO();
    return vo;
  }
  
  // POST: 등록
  /*
  curl --location 'http://localhost:8080/api/employ' \
  --header 'Content-Type: application/json' \
  --data '{
      "name": "gdhong"
  }'
  */
  @PostMapping("/api/employ")
  public EmployVO insert(@RequestBody EmployVO param) {
    return param;
  }
  
  // PUT: 수정
  /*
  curl --location --request PUT 'http://localhost:8080/api/employ' \
  --header 'Content-Type: application/json' \
  --data '{
      "id": "00s0xbssk4qvz9",
      "name": "shlee"
  }'
   */
  @PutMapping("/api/employ")
  public EmployVO update(@RequestBody EmployVO param) {
    return param;
  }
  
}
