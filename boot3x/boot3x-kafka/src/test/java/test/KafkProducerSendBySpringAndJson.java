package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import spring.sample.app.vo.MyTopicVo;
import spring.sample.common.constant.Constant;
import spring.sample.common.util.Uuid;

public class KafkProducerSendBySpringAndJson {
  
  public static final Logger log = LoggerFactory.getLogger(KafkProducerSendBySpringAndJson.class);
  
  String BOOTSTRAP_SERVERS = "172.28.200.30:9092";
  String topicName = "mytopic1";
  List<String> names = Arrays.asList("scott", "tiger", "ecycle", "yslee", "yslee", "sckim", "dhkim", "jkpark", "yhlee", "jckim", "mgkim");
  
  @Test
  public void sendBySpringAndJson() {
    Map<String, Object> config = new HashMap<String, Object>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
//  config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
//  config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);
//  config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, com.fasterxml.jackson.databind.JsonSerializer.class);
    
    org.apache.kafka.common.serialization.StringSerializer keySerializer = 
        new StringSerializer();
    
    ObjectMapper objectMapper = null;
    {
      objectMapper = new ObjectMapper();
      JavaTimeModule module = new JavaTimeModule();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);
      module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
      objectMapper.registerModule(module);
    }
    org.springframework.kafka.support.serializer.JsonSerializer valueSerializer = 
        new JsonSerializer(objectMapper);
    
    org.springframework.kafka.core.ProducerFactory<String, Object> producerFactory = 
        new org.springframework.kafka.core.DefaultKafkaProducerFactory<>(config, keySerializer, valueSerializer);
//        new org.springframework.kafka.core.DefaultKafkaProducerFactory<>(config);
    
    org.springframework.kafka.core.KafkaTemplate<String, Object> kafkaTemplate = 
        new org.springframework.kafka.core.KafkaTemplate<>(producerFactory);
    
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
    
    org.springframework.messaging.Message<MyTopicVo> message =
        org.springframework.messaging.support.MessageBuilder
          .withPayload(data)
          .setHeader(org.springframework.kafka.support.KafkaHeaders.TOPIC, topicName)
          .build();
    
    java.util.concurrent.CompletableFuture<org.springframework.kafka.support.SendResult<String, Object>> future = 
        kafkaTemplate.send(message);
    
    future.whenComplete((result, ex) -> {
      if (ex == null) {
        log.info("producer: success >>> message: {}, offset: {}",
            result.getProducerRecord().value().toString(), result.getRecordMetadata().offset());
      } else {
        log.info("producer: failure >>> message: {}", ex.getMessage());
      }
    });
  }
}
