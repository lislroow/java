package test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import samples.KafkaProducerMain;
import samples.UuidMain;

public class MyTopicProducer {
  
  List<String> names = Arrays.asList("scott", "tiger", "ecycle", "yslee", "yslee", "sckim", "dhkim", "jkpark", "yhlee", "jckim", "mgkim");
  
  @Test
  public void mytopic() {
    String topicName = "mytopic";
    Map<String, Object> data = new HashMap<String, Object>();
    String id = UuidMain.create();
    data.put("id", id);
    data.put("name", names.get(new Random().nextInt(names.size())));
    data.put("createDate", LocalDateTime.now());
    data.put("modifyDate", LocalDateTime.now());
    data.put("createId", id);
    data.put("modifyId", id);
    new KafkaProducerMain().send(topicName, data);
  }
}
