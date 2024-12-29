package spring.component.app.controller;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.constant.Constant;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.redis.RedisSupport;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = Constant.ENABLED, havingValue = "true", matchIfMissing = false)
public class RedisController {
  
  final RedisSupport redisSupport;
  //final RedisTemplate<String, Object> redisTemplate;
  
  @PostConstruct
  public void init() {
    redisSupport.setValue("custom.value.string", "value-string");
    redisSupport.setHash("custom.hash.string", "hash-key-string", "hash-value-string");
  }
  
  @GetMapping("/v1/redis/keys")
  public ResponseDto<Set<String>> keys() {
    Set<String> keys = redisSupport.keys();
    return ResponseDto.body(keys);
  }
  
  @GetMapping("/v1/redis/key/{key}")
  public ResponseDto<Set<String>> key(@PathVariable("key") String key) {
    Set<String> keys = redisSupport.key(key);
    return ResponseDto.body(keys);
  }
  
  @GetMapping("/v1/redis/value/{key}")
  public ResponseDto<String> get(@PathVariable("key") String key) {
    String value = redisSupport.getValue(key);
    return ResponseDto.body(value);
  }
  
  @GetMapping("/v1/redis/hash/{key}/{hashKey}")
  public ResponseDto<String> hget(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey) {
    String hashValue = redisSupport.getHash(key, hashKey);
    return ResponseDto.body(hashValue);
  }
  
  @PutMapping("/v1/redis/value/{key}")
  public ResponseDto<String> set(@PathVariable("key") String key, @RequestParam("value") String value) {
    redisSupport.setValue(key, value);
    value = redisSupport.getValue(key);
    return ResponseDto.body(value);
  }
  
  @PutMapping("/v1/redis/hash/{key}/{hashKey}")
  public ResponseDto<String> hset(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey,
      @RequestParam("value") String value) {
    redisSupport.setHash(key, hashKey, value);
    String hashValue = redisSupport.getHash(key, hashKey);
    return ResponseDto.body(hashValue);
  }
  
  @DeleteMapping("/v1/redis/value/{key}")
  public ResponseDto<?> del(@PathVariable("key") String key) {
    redisSupport.delValue(key);
    return ResponseDto.body();
  }
  
  @DeleteMapping("/v1/redis/hash/{key}/{hashKey}")
  public ResponseDto<?> hdel(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey) {
    redisSupport.deleteHash(key, hashKey);
    return ResponseDto.body();
  }
}