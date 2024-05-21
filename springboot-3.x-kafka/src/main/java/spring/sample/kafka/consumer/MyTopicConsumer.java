package spring.sample.kafka.consumer;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.sample.kafka.api.mytopic.MyTopicMapper;

@Service
public class MyTopicConsumer implements ConsumerSeekAware {
  
  private static final Logger log = LoggerFactory.getLogger(MyTopicConsumer.class);
  
  @Autowired
  private MyTopicMapper mapper;
  
//  private ConsumerSeekCallback seekCallback;
  
  @KafkaListener(topics = "mytopic", containerFactory = "kafkaListener")
  @Transactional
  public void mytopicListener(Map<String, Object> data) {
    log.info("data={}", data);
    if (data.get("data") instanceof java.util.Map) {
      Map<String, Object> param = (Map<String, Object>) data.get("data");
      mapper.insert(param);
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
