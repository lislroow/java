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
  @KafkaListener(id = "mytopic1Listener", topics = "mytopic1", containerFactory = "kafkaListener", autoStartup = "false")
  @Transactional
  public void mytopicListener(MyTopicVO data, Acknowledgment ack) {
    try {
      mapper.insert(data);
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
