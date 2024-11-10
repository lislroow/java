package test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import spring.sample.kafka.mytopic.dto.MyTopicVO;
import spring.sample.util.Uuid;

public class KafkProducerSendByKafkaAndJson {
  
  String BOOTSTRAP_SERVERS = "172.28.200.30:9092";
  String topicName = "mytopic1";
  List<String> names = Arrays.asList("scott", "tiger", "ecycle", "yslee", "yslee", "sckim", "dhkim", "jkpark", "yhlee", "jckim", "mgkim");
  
  @Test
  public void sendByKafkaAndJson() {
    Map<String, Object> config = new HashMap<String, Object>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
    
    //config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
    //config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);
    //config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, com.fasterxml.jackson.databind.JsonSerializer.class);
    
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
    
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    org.apache.kafka.common.serialization.StringSerializer keySerializer = new StringSerializer();
    org.springframework.kafka.support.serializer.JsonSerializer valueSerializer = null;
    valueSerializer = new JsonSerializer(objectMapper);
    org.apache.kafka.clients.producer.KafkaProducer<String, Object> producer = 
        //new org.apache.kafka.clients.producer.KafkaProducer<>(config);
        new org.apache.kafka.clients.producer.KafkaProducer<>(config,
            keySerializer, valueSerializer);
    java.util.concurrent.Future<org.apache.kafka.clients.producer.RecordMetadata> future = 
        producer.send(new org.apache.kafka.clients.producer.ProducerRecord<String, Object>(topicName, data));
    producer.close();
  }
}
