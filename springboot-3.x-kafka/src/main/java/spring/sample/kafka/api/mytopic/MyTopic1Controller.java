package spring.sample.kafka.api.mytopic;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.sample.kafka.api.mytopic.dto.MyTopicVO;
import spring.sample.kafka.producer.TopicProducer;
import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.config.mybatis.PagedList;
import spring.sample.mybatis.util.Uuid;

@RestController
public class MyTopic1Controller {

  private MyTopic1Service service;
  private TopicProducer<MyTopicVO> producer;
  
  public MyTopic1Controller(MyTopic1Service service,
      TopicProducer<MyTopicVO> producer) {
    this.service = service;
    this.producer = producer;
  }
  
// GET: 조회
/*
curl -X GET http://localhost:8080/api/mytopic1/all
*/
  @GetMapping("/api/mytopic1/all")
  public List<MyTopicVO> selectAll() {
    List<MyTopicVO> res = service.selectAll();
    return res;
  }

/*
curl -X GET http://localhost:8080/api/mytopic1/list?page=2&pageSize=3
*/
  @GetMapping("/api/mytopic1/list")
  public PagedList<MyTopicVO> selectList(Pageable param) {
    PagedList<MyTopicVO> res = service.selectList(param);
    return res;
  }
  
// POST: 등록
/*
curl -X POST http://localhost:8080/api/mytopic1/test-publish
*/
  @PostMapping("/api/mytopic1/test-publish")
  public void testPublish() {
    List<String> names = Arrays.asList("scott", "tiger", "ecycle", "yslee", "yslee", "sckim", "dhkim", "jkpark", "yhlee", "jckim", "mgkim");
    MyTopicVO data = new MyTopicVO();
    {
      String id = Uuid.create();
      data.setId(id);
      data.setName(names.get(new Random().nextInt(names.size())));
      data.setCreateDate(LocalDateTime.now());
      data.setModifyDate(LocalDateTime.now());
      data.setCreateId(id);
      data.setModifyId(id);
    }
    producer.send("mytopic1", data);
  }
}
