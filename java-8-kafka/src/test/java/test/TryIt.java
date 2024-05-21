package test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import samples.kafka.TopicProducer;

public class TryIt {
  
  @Test
  public void test1() {
    String topicName = "mytopic";
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("name", "scott");
    data.put("age", 33);
    data.put("birth", LocalDate.of(1999, 1, 2));
    new TopicProducer().send(topicName, data);
  }
}
