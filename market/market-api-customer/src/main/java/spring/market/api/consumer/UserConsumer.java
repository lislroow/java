package spring.market.api.consumer;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.market.api.repository.MyInfoRepository;

@Component
@RequiredArgsConstructor
public class UserConsumer {
  
  private final MyInfoRepository repository;
  
  //@KafkaListener(topics = "CustomOAuth2UserService.loadUser", containerFactory = "kafkaListener")
  //@Transactional
  //public void loadUser(User user) {
  //  Customer entity = Customer.builder()
  //      .id(user.getId())
  //      .email(user.getEmail())
  //      .name(user.getNickname())
  //      .createTime(LocalDateTime.now())
  //      .modifyTime(LocalDateTime.now())
  //      .build();
  //  this.repository.save(entity);
  //}
}
