package spring.sample.kafka.consumer;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.sample.kafka.api.mytopic.MyTopic1Mapper;
import spring.sample.kafka.api.mytopic.dto.MyTopicVO;

@Service
public class MyTopic1Consumer implements ConsumerSeekAware {
  
  private static final Logger log = LoggerFactory.getLogger(MyTopic1Consumer.class);
  
  @Autowired
  private MyTopic1Mapper mapper;
  
//  private ConsumerSeekCallback seekCallback;
  
  @KafkaListener(topics = "mytopic1", containerFactory = "kafkaListener")
  @Transactional
  public void mytopicListener(MyTopicVO data, Acknowledgment ack) {
    try {
      mapper.insert(data);
//      int i = 1/0;
      ack.acknowledge();
    } catch (Exception e) {
      ack.nack(Duration.ofMillis(200));
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
