package spring.sample.kafka.producer;

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
