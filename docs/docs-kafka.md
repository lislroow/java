### 99. snippets

- kafka 는 새로 구성을 해야하는 상태
- 아래는 backup code

#### 1) KafkaConfig

```java
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
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
```

#### 2) TopicProducer

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TopicProducer<T> {
  
  public static final Logger log = LoggerFactory.getLogger(TopicProducer.class);
  
  KafkaTemplate<String, Object> kafkaTemplate;
  
  public TopicProducer(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }
  
  public void send(String topicName, T data) {
    org.springframework.messaging.Message<T> message =
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
```

#### 3) MyTopicConsumer

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dao.MyTopicDao;
import spring.sample.app.vo.MyTopicVo;

//@Service
@Slf4j
public class MyTopicConsumer implements ConsumerSeekAware {
  
  @Autowired
  private MyTopicDao myTopicDao;
  
//  private ConsumerSeekCallback seekCallback;
  
  //### '@KafkaListener' 의 autoStartup 속성에 대한 처리 과정 분석 ###
  // 
  // autoStartup 속성은 빈 문자열이 기본값(true 로 설정)이며, "false" 문자열일 경우에는 '@KafkaListener' 선언된 메소드가 초기화 과정 이후에 바로 실행하지 않습니다.
  // 
  // '@KafkaListener' 가 선언된 메소드는 KafkaListenerEndpointRegistry 의 listenerContainers 에 등록되고 실행(start) 되며
  // 초기화 과정에서 바로 실행하지 않았을 경우, KafkaListenerEndpointRegistry 을 사용하여 특정 topic listener 가 start / stop 을 할 수 있습니다.
  //   see) MyTopicController 클래스
  // 
  // KafkaListenerEndpointRegistry 의 listener 초기화 과정 및 autoStart 실행에 대한 주요 코드 설명 입니다.
  //   1) 'spring-beans' 의 KafkaListenerAnnotationBeanPostProcessor.postProcessAfterInitialization 에서
  //      '@KafkaListener' 가 선언된 메소드를 탐색합니다.
  //   2) 'spring-beans' 의 KafkaListenerAnnotationBeanPostProcessor.processKafkaListener 에서
  //      '@KafkaListener' 가 선언된 메소드는 MethodKafkaListenerEndpoint 타입으로 객체화 됩니다. (proxy, reflection)
  //      MethodKafkaListenerEndpoint 객체의 초기화 과정에서 '@KafkaListener' 의 autoStartup 속성이 정의 됩니다. 
  //   3) 'spring-beans' 의 KafkaListenerEndpointRegistry.startIfNecessary(MessageListenerContainer) 에서 autoStartup 속성값에 따라 start 가 호출됩니다.
  //      실제 start 가 호출되는 코드는 ConcurrentMessageListenerContainer.doStart() 메소드 입니다.
  @KafkaListener(id = "mytopicListener", topics = "mytopic", containerFactory = "kafkaListener", autoStartup = "false")
  @Transactional
  public void mytopicListener(MyTopicVo data, Acknowledgment ack) {
    try {
      myTopicDao.insert(data);
//      int i = 1/0;
//      if (ack != null) {
//        ack.acknowledge();
//      }
    } catch (Exception e) {
//      if (ack != null) {
//        ack.nack(Duration.ofMillis(200));
//      }
      log.error("", e);
      throw e;
    }
  }
  
//  @Override
//  public void registerSeekCallback(ConsumerSeekCallback callback) {
//    this.seekCallback = callback;
//  }
//  
//  @Override
//  public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
//    callback.seek("mytopic", 0, 1);
//  }
  
}
```

#### 4) application.properties

```properties
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.bootstrap-servers=172.28.200.30:9092
spring.kafka.consumer.group-id=mygroup
```