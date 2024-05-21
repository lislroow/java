package test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import samples.kafka.TopicProducer;
import samples.util.Uuid;

public class TryIt {
  
  List<String> names = Arrays.asList("scott", "tiger", "ecycle", "yslee", "yslee", "sckim", "dhkim", "jkpark", "yhlee", "jckim", "mgkim");
  
  @Test
  public void test1() {
    String topicName = "mytopic";
    Map<String, Object> data = new HashMap<String, Object>();
    String id = Uuid.create();
    data.put("id", id);
    data.put("name", names.get(new Random().nextInt(names.size())));
    data.put("createDate", LocalDateTime.now());
    data.put("modifyDate", LocalDateTime.now());
    data.put("createId", id);
    data.put("modifyId", id);
    new TopicProducer().send(topicName, data);
  }
}
