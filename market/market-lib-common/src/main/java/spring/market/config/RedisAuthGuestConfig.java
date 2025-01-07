package spring.market.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.custom.common.redis.RedisSupport;
import spring.market.common.constant.MarketConstant;
import spring.market.common.enumcode.REDIS_TYPE;
import spring.market.config.properties.RedisProperties;

//@Configuration
//@ConditionalOnProperty(prefix = "market.redis.auth-guest", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
//@EnableConfigurationProperties(spring.market.config.properties.RedisProperties.class)
public class RedisAuthGuestConfig {

  @Autowired
  spring.market.config.properties.RedisProperties redisProperties;
  
  @Bean(name = MarketConstant.REDIS.AUTH_GUEST + "RedisConnectionFactory")
  RedisConnectionFactory redisConnectionFactory() {
    RedisProperties.Configure configure = redisProperties.getConfigure(REDIS_TYPE.AUTH_GUEST);
    return new LettuceConnectionFactory(configure.getHost(), configure.getPort());
  }
  
  @Bean(name = MarketConstant.REDIS.AUTH_GUEST + "RedisTemplate")
  RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
    return redisTemplate;
  }
  
  @Bean(name = MarketConstant.REDIS.AUTH_GUEST + "RedisSupport")
  RedisSupport redisSupport(ModelMapper modelMapper, ObjectMapper objectMapper) {
    return new RedisSupport(redisTemplate(), modelMapper, objectMapper);
  }
}
