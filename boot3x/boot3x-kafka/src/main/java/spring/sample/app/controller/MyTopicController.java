package spring.sample.app.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import spring.sample.app.producer.TopicProducer;
import spring.sample.app.service.MyTopicService;
import spring.sample.app.vo.MyTopicVo;
import spring.sample.common.mybatis.Pageable;
import spring.sample.common.mybatis.PagedList;
import spring.sample.common.util.Uuid;

//@RestController
@RequiredArgsConstructor
public class MyTopicController {

  final MyTopicService service;
  final KafkaListenerEndpointRegistry registry;
  final TopicProducer<MyTopicVo> producer;
  
  @GetMapping("/api/mytopic/all")
  public List<MyTopicVo> selectAll() {
    List<MyTopicVo> res = service.selectAll();
    return res;
  }

  @GetMapping("/api/mytopic/list")
  public PagedList<MyTopicVo> selectList(Pageable param) {
    PagedList<MyTopicVo> res = service.selectList(param);
    return res;
  }
  
  @PostMapping("/api/mytopic/test-publish")
  public void testPublish() {
    List<String> names = Arrays.asList("scott", "tiger", "ecycle", "yslee", "yslee", "sckim", "dhkim", "jkpark", "yhlee", "jckim", "mgkim");
    MyTopicVo data = new MyTopicVo();
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
  
  @PostMapping("/api/mytopic/start")
  public void start() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopic1Listener");
    listener.start();
  }
  
  @PostMapping("/api/mytopic/stop")
  public void stop() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopic1Listener");
    listener.stop();
  }
}
