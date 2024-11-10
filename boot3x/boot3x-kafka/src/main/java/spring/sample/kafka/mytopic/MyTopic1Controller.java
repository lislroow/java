package spring.sample.kafka.mytopic;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.sample.config.mybatis.Pageable;
import spring.sample.config.mybatis.PagedList;
import spring.sample.kafka.mytopic.dto.MyTopicVO;
import spring.sample.kafka.producer.TopicProducer;
import spring.sample.util.Uuid;

@RestController
public class MyTopic1Controller {

  private MyTopic1Service service;
  private KafkaListenerEndpointRegistry registry;
  private TopicProducer<MyTopicVO> producer;
  
  public MyTopic1Controller(MyTopic1Service service,
      KafkaListenerEndpointRegistry registry,
      TopicProducer<MyTopicVO> producer) {
    this.service = service;
    this.registry = registry;
    this.producer = producer;
  }
  
// GET: 조회
/*
curl -X GET http://localhost:8888/api/mytopic1/all
*/
  @GetMapping("/api/mytopic1/all")
  public List<MyTopicVO> selectAll() {
    List<MyTopicVO> res = service.selectAll();
    return res;
  }

/*
curl -X GET http://localhost:8888/api/mytopic1/list?page=2&pageSize=3
*/
  @GetMapping("/api/mytopic1/list")
  public PagedList<MyTopicVO> selectList(Pageable param) {
    PagedList<MyTopicVO> res = service.selectList(param);
    return res;
  }
  
// POST: 등록
/*
curl -X POST http://localhost:8888/api/mytopic1/test-publish
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

//POST: mytopic1 listener 실행
/*
curl -X POST http://localhost:8888/api/mytopic1/start
*/
  @PostMapping("/api/mytopic1/start")
  public void start() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopic1Listener");
    listener.start();
  }
  
// POST: mytopic listener 중지
/*
curl -X POST http://localhost:8888/api/mytopic1/stop
*/
  @PostMapping("/api/mytopic1/stop")
  public void stop() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopic1Listener");
    listener.stop();
  }
}
