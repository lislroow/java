package spring.sample.app.datasource;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.sample.app.employ.dto.EmployPageREQ;
import spring.sample.app.employ.dto.EmployVO;
import spring.sample.config.mybatis.Pageable;
import spring.sample.config.mybatis.PagedList;

@RestController
public class OracleController {

  private OracleService service;
  
  public OracleController(OracleService service) {
    this.service = service;
  }
  
  // GET: 조회
  /*
    curl --location 'http://localhost:8888/api/employ/oracle/all'
  */
  @GetMapping("/api/employ/oracle/all")
  public List<EmployVO> selectAll() {
    List<EmployVO> res = service.selectAll();
    return res;
  }

  /*
    curl --location 'http://localhost:8888/api/employ/oracle/list?page=2&pageSize=3'
  */
  @GetMapping("/api/employ/oracle/list")
  public PagedList<EmployVO> selectList(Pageable param) {
    PagedList<EmployVO> res = service.selectList(param);
    return res;
  }
  
  /*
    curl --location 'http://localhost:8888/api/employ/oracle/listByName?page=1&pageSize=5&name=lee'
   */
  @GetMapping("/api/employ/oracle/listByName")
  public PagedList<EmployVO> selectListByName(EmployPageREQ param) {
    PagedList<EmployVO> res = service.selectListByName(param);
    return res;
  }
  
  // POST: 등록
  /*
  curl --location 'http://localhost:8888/api/employ/oracle' \
  --header 'Content-Type: application/json' \
  --data '{
      "name": "gdhong"
  }'
  */
  @PostMapping("/api/employ/oracle")
  public EmployVO insert(@RequestBody EmployVO param) {
    EmployVO res = service.insert(param);
    return res;
  }
  
  // PUT: 수정
  /*
  curl --location --request PUT 'http://localhost:8888/api/employ/oracle' \
  --header 'Content-Type: application/json' \
  --data '{
      "id": "00s0xbssk4qvz9",
      "name": "shlee"
  }'
   */
  @PutMapping("/api/employ/oracle")
  public EmployVO update(@RequestBody EmployVO param) {
    EmployVO res = service.update(param);
    return res;
  }
  
  // DELETE: 수정
  /*
  curl --location --request DELETE 'http://localhost:8888/api/employ/oracle' \
  --header 'Content-Type: application/json' \
  --data '{
      "id": "00sl3e2kacak4i"
  }'
   */
  @DeleteMapping("/api/employ/oracle")
  public void delete(@RequestBody EmployVO param) {
    service.delete(param);
  }
  
}
