package spring.sample.kafka.consumer;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import spring.sample.MainApplication;
import spring.sample.kafka.api.mytopic.MyTopic1Mapper;
import spring.sample.kafka.api.mytopic.dto.MyTopicVO;
import spring.sample.kafka.consumer.MyTopic1Consumer;
import spring.sample.mybatis.util.Uuid;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyTopic1Consumer_ServiceTest {
  
  @Autowired
  private MyTopic1Consumer consumer;
  
  @Autowired
  private MyTopic1Mapper mapper;

  @Test
  @Order(1)
  public void step1() {
    List<MyTopicVO> list = null;
    list = mapper.selectAll();
    System.out.println("select list="+list);
  }
  
  @Test
  @Order(2)
  public void step2() {
    MyTopicVO data = new MyTopicVO();
    data.setId(Uuid.create());
    data.setCreateDate(LocalDateTime.now());
    data.setModifyDate(LocalDateTime.now());
    data.setName("scott");
    consumer.mytopicListener(data, null);
  }
  
  @Test
  @Order(3)
  public void step3() {
    List<MyTopicVO> list = null;
    list = mapper.selectAll();
    System.out.println("select list="+list);
  }
}
