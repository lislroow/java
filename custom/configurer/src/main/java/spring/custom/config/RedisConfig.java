package spring.custom.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.custom.common.constant.Constant;
import spring.custom.common.redis.RedisClient;

@Configuration
@ConditionalOnProperty(prefix = "spring.data.redis", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
public class RedisConfig {

  @Autowired
  RedisProperties redisProperties;
  
  @Bean
  RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
  }
  
  @Bean
  RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
    return redisTemplate;
  }
  
  @Bean
  RedisClient redisClient(ModelMapper modelMapper, ObjectMapper objectMapper) {
    return new RedisClient(redisTemplate(), modelMapper, objectMapper);
  }
}
