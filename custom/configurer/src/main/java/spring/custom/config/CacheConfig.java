package spring.custom.config;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.CACHE;

@Configuration
@ConditionalOnProperty(prefix = "spring.data.redis", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
public class CacheConfig {
  
  @Autowired
  CacheProperties cacheProperties;
  
  @Bean
  RedisCacheConfiguration redisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(cacheProperties.getRedis().getTimeToLive())
        .disableCachingNullValues()
        .serializeValuesWith(RedisSerializationContext.SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }
  
  @Bean
  RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    Map<String, RedisCacheConfiguration> cacheConfigurations = Arrays.asList(CACHE.values()).stream()
      .collect(Collectors.toMap(
          item -> item.cacheName(),
          item -> RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
            .entryTtl(item.ttl())));
    return (builder) -> builder.withInitialCacheConfigurations(cacheConfigurations);
    
    /*
    return (builder) -> builder
        .withCacheConfiguration(
            "cache:common-code",
            RedisCacheConfiguration.defaultCacheConfig()
              .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
              .entryTtl(Duration.ofMinutes(20))
        )
        .withCacheConfiguration(
            "cache:scientist",
            RedisCacheConfiguration.defaultCacheConfig()
              .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
              .entryTtl(Duration.ofMinutes(10))
        );
    */
  }
  
}
