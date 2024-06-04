package spring.sample.mybatis.api.employ;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.sample.mybatis.api.employ.dto.EmployPageREQ;
import spring.sample.mybatis.api.employ.dto.EmployVO;
import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.config.mybatis.PagedList;

@RestController
public class EmployController {

  private EmployService service;
  
  public EmployController(EmployService service) {
    this.service = service;
  }
  
  // GET: 조회
  /*
    curl --location 'http://localhost:8080/api/employ/all'
  */
  @GetMapping("/api/employ/all")
  public List<EmployVO> selectAll() {
    List<EmployVO> res = service.selectAll();
    return res;
  }

  /*
    curl --location 'http://localhost:8080/api/employ/list?page=2&pageSize=3'
  */
  @GetMapping("/api/employ/list")
  public PagedList<EmployVO> selectList(Pageable param) {
    PagedList<EmployVO> res = service.selectList(param);
    return res;
  }
  
  /*
    curl --location 'http://localhost:8080/api/employ/listByName?page=1&pageSize=5&name=lee'
   */
  @GetMapping("/api/employ/listByName")
  public PagedList<EmployVO> selectListByName(EmployPageREQ param) {
    PagedList<EmployVO> res = service.selectListByName(param);
    return res;
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
    EmployVO res = service.insert(param);
    return res;
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
    EmployVO res = service.update(param);
    return res;
  }
  
  // DELETE: 수정
  /*
  curl --location --request DELETE 'http://localhost:8080/api/employ' \
  --header 'Content-Type: application/json' \
  --data '{
      "id": "00sl3e2kacak4i"
  }'
   */
  @DeleteMapping("/api/employ")
  public void delete(@RequestBody EmployVO param) {
    service.delete(param);
  }
  
}
