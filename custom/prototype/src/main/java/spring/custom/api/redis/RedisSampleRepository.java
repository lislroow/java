package spring.custom.api.redis;

import org.springframework.data.repository.CrudRepository;

import spring.custom.api.vo.RedisSampleVo;

public interface RedisSampleRepository extends CrudRepository<RedisSampleVo, Integer> {
  
}
