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
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedList;
import spring.custom.common.util.Uuid;
import spring.sample.app.service.KafkaService;
import spring.sample.app.vo.EmployVo;
import spring.sample.common.kafka.TopicProducer;

//@RestController
@RequiredArgsConstructor
public class KafkaController {

  final KafkaService service;
  final KafkaListenerEndpointRegistry registry;
  final TopicProducer<EmployVo> producer;
  
  @GetMapping("/v1/kafka/mytopic/all")
  public List<EmployVo> selectAll() {
    List<EmployVo> res = service.selectAll();
    return res;
  }

  @GetMapping("/v1/kafka/mytopic/list")
  public PagedList<EmployVo> selectList(PageRequest param) {
    PagedList<EmployVo> res = service.selectList(param);
    return res;
  }
  
  @PostMapping("/v1/kafka/mytopic/publish")
  public void testPublish() {
    List<String> names = Arrays.asList("scott", "tiger", "ecycle", "yslee", "yslee", "sckim", "dhkim", "jkpark", "yhlee", "jckim", "mgkim");
    EmployVo data = new EmployVo();
    {
      Integer id = 1;
      data.setId(id);
      data.setName(names.get(new Random().nextInt(names.size())));
      data.setCreateDate(LocalDateTime.now());
      data.setModifyDate(LocalDateTime.now());
      data.setCreateId(id);
      data.setModifyId(id);
    }
    producer.send("mytopic1", data);
  }
  
  @PostMapping("/v1/kafka/mytopic/start")
  public void start() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopic1Listener");
    listener.start();
  }
  
  @PostMapping("/v1/kafka/mytopic/stop")
  public void stop() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopic1Listener");
    listener.stop();
  }
}
