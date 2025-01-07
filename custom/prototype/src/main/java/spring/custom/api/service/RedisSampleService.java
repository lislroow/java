package spring.custom.api.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.custom.api.redis.RedisSampleRepository;
import spring.custom.api.vo.RedisSampleVo;

@Service
@RequiredArgsConstructor
public class RedisSampleService {
  
  final RedisSampleRepository redisCrudRepository;
  
  public void addSample(RedisSampleVo param) {
    redisCrudRepository.save(param);
  }
  
}
