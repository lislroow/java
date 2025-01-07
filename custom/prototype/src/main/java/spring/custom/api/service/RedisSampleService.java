package spring.custom.api.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.custom.api.redis.RedisSampleRepository;
import spring.custom.api.vo.RedisSampleVo;
import spring.custom.common.constant.Constant;

@Service
@ConditionalOnProperty(prefix = "spring.data.redis", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class RedisSampleService {
  
  final RedisSampleRepository redisCrudRepository;
  
  public void addSample(RedisSampleVo param) {
    redisCrudRepository.save(param);
  }
  
}
