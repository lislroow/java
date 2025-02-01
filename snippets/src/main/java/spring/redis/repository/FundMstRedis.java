package spring.redis.repository;

import org.springframework.data.repository.CrudRepository;

import spring.redis.vo.FundMstRvo;

public interface FundMstRedis extends CrudRepository<FundMstRvo, String> {
  
}
