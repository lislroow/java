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

import spring.sample.Boot3xKafkaMain;
import spring.sample.app.consumer.MyTopicConsumer;
import spring.sample.app.dao.MyTopicDao;
import spring.sample.app.vo.MyTopicVo;
import spring.sample.common.util.Uuid;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Boot3xKafkaMain.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyTopicConsumer_ServiceTest {
  
  @Autowired
  private MyTopicConsumer myTopicConsumer;
  
  @Autowired
  private MyTopicDao myTopicDao;

  @Test
  @Order(1)
  public void step1() {
    List<MyTopicVo> list = null;
    list = myTopicDao.selectAll();
    System.out.println("select list="+list);
  }
  
  @Test
  @Order(2)
  public void step2() {
    MyTopicVo data = new MyTopicVo();
    data.setId(Uuid.create());
    data.setCreateDate(LocalDateTime.now());
    data.setModifyDate(LocalDateTime.now());
    data.setName("scott");
    myTopicConsumer.mytopicListener(data, null);
  }
  
  @Test
  @Order(3)
  public void step3() {
    List<MyTopicVo> list = null;
    list = myTopicDao.selectAll();
    System.out.println("select list="+list);
  }
}
