package spring.custom.api.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@RedisHash("sample")
@Data
public class RedisSampleVo implements java.io.Serializable {
  
  @Id
  private Integer id;
  
  private String name;
  
  private Integer age;
  
}
