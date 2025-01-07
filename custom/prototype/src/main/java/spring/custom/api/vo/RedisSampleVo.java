package spring.custom.api.vo;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@RedisHash("sample")
@Data
public class RedisSampleVo implements java.io.Serializable {

  private Integer id;
  private String name;
  
}
