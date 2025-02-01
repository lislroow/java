package main.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisMain {
  
  static RedisConnectionFactory redisConnectionFactory() {
    LettuceConnectionFactory factory = new LettuceConnectionFactory("rocky8-market", 6310);
    factory.afterPropertiesSet();
    return factory;
  }
  
  public static RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new JdkSerializationRedisSerializer());
    template.afterPropertiesSet();
    return template;
  }
  
  public static void main(String[] args) {
    RedisTemplate<String, String> template = redisTemplate();
    template.opsForValue().set("opsForValue:key", "value");
    String value = template.opsForValue().get("opsForValue:key");
    System.out.println(value);
  }
}
