package samples.kafka;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;

public class TopicProducer {
  
  public static final String BOOTSTRAP_SERVERS = "172.28.200.30:9092";
  public static final String GROUP_ID = "mygroup";
  private Properties props = new Properties();
  
  public TopicProducer() {
    this(BOOTSTRAP_SERVERS);
  }
  
  public TopicProducer(String bootstrapServers) {
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
  }
  
  public void send(String topic, Map<String, Object> data) {
    Producer<String, String> producer = new KafkaProducer<>(props);
    JSONObject json = new JSONObject();
    json.put("data", data);
    String message = json.toString();
    // cluster 모드일 경우 key 값에 대한 고려가 필요합니다.
    producer.send(new ProducerRecord<String, String>(topic, message));
    producer.close();
  }
}
