package samples.kafka;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;

public class TopicProducer {
  
  public static final String BOOTSTRAP_SERVERS = "172.28.200.30:9092";
  private Properties props = new Properties();
  
  public TopicProducer() {
    this(BOOTSTRAP_SERVERS);
  }
  
  public TopicProducer(String bootstrapServers) {
    props.put("bootstrap.servers", bootstrapServers);
    props.put("key.serializer", org.apache.kafka.common.serialization.StringSerializer.class);
    props.put("value.serializer", org.apache.kafka.common.serialization.StringSerializer.class);
  }
  
  public void send(String topic, Map<String, Object> data) {
    Producer<String, String> producer = new KafkaProducer<>(props);
    JSONObject json = new JSONObject();
    json.put("data", data);
    String message = json.toString();
    producer.send(new ProducerRecord<String, String>(topic, message));
    producer.close();
  }
}
