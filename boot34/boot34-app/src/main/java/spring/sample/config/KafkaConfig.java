package spring.sample.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import spring.sample.common.constant.Constant;

//@Configuration
//@EnableConfigurationProperties({KafkaProperties.class})
public class KafkaConfig {

  @Autowired
  KafkaProperties properties;
  
  @Bean
  ProducerFactory<String, Object> producerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
//    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    
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
    
    return new DefaultKafkaProducerFactory<>(config, keySerializer, valueSerializer);
  }
  
  @Bean
  KafkaTemplate<String, Object> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
  
  @Bean
  ConsumerFactory<String, Object> consumerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    //config.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
    //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getAutoOffsetReset());
    //config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    ObjectMapper objectMapper = null;
    {
      objectMapper = new ObjectMapper();
      JavaTimeModule module = new JavaTimeModule();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);
      module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
      objectMapper.registerModule(module);
    }
    
    //config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    config.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // [issue] the class '...' is not in the trusted packages
    //config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Map.class.getName());
    ConsumerFactory factory = new DefaultKafkaConsumerFactory(
        config, new StringDeserializer(), new JsonDeserializer(objectMapper));
    return factory;
  }
  
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListener() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    //factory.getContainerProperties()
    //  .setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    factory.getContainerProperties()
      .setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);
    return factory;
  }
}
