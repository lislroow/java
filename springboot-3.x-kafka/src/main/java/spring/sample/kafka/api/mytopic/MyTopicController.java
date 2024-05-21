package spring.sample.kafka.api.mytopic;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.sample.kafka.api.mytopic.dto.MyTopicVO;
import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.config.mybatis.PagedList;

@RestController
public class MyTopicController {

  private MyTopicService service;
  
  public MyTopicController(MyTopicService service) {
    this.service = service;
  }
  
  // GET: 조회
  /*
    curl --location 'http://localhost:8080/api/mytopic/all'
  */
  @GetMapping("/api/mytopic/all")
  public List<MyTopicVO> selectAll() {
    List<MyTopicVO> res = service.selectAll();
    return res;
  }

  /*
    curl --location 'http://localhost:8080/api/mytopic/list?page=2&pageSize=3'
  */
  @GetMapping("/api/mytopic/list")
  public PagedList<MyTopicVO> selectList(Pageable param) {
    PagedList<MyTopicVO> res = service.selectList(param);
    return res;
  }
}
