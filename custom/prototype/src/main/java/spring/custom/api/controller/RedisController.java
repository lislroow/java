package spring.custom.api.controller;

import java.util.Set;

//import javax.annotation.PostConstruct;
//import jakarta.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.constant.Constant;
import spring.custom.common.redis.RedisSupport;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
public class RedisController {
  
  final RedisSupport redisSupport;
  //final RedisTemplate<String, Object> redisTemplate;
  
  @GetMapping("/v1/redis/keys")
  public ResponseEntity<Set<String>> keys() {
    Set<String> keys = redisSupport.keys();
    return ResponseEntity.ok(keys);
  }
  
  @GetMapping("/v1/redis/key/{key}")
  public ResponseEntity<Set<String>> key(@PathVariable("key") String key) {
    Set<String> keys = redisSupport.keys(key);
    return ResponseEntity.ok(keys);
  }
  
  @GetMapping("/v1/redis/value/{key}")
  public ResponseEntity<String> get(@PathVariable("key") String key) {
    String value = redisSupport.getValue(key);
    return ResponseEntity.ok(value);
  }
  
  @GetMapping("/v1/redis/hash/{key}/{hashKey}")
  public ResponseEntity<String> hget(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey) {
    String hashValue = redisSupport.getHash(key, hashKey);
    return ResponseEntity.ok(hashValue);
  }
  
  @PutMapping("/v1/redis/value/{key}")
  public ResponseEntity<String> set(@PathVariable("key") String key, @RequestParam("value") String value) {
    redisSupport.setValue(key, value);
    value = redisSupport.getValue(key);
    return ResponseEntity.ok(value);
  }
  
  @PutMapping("/v1/redis/hash/{key}/{hashKey}")
  public ResponseEntity<String> hset(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey,
      @RequestParam("value") String value) {
    redisSupport.setHash(key, hashKey, value);
    String hashValue = redisSupport.getHash(key, hashKey);
    return ResponseEntity.ok(hashValue);
  }
  
  @DeleteMapping("/v1/redis/value/{key}")
  public ResponseEntity<?> del(@PathVariable("key") String key) {
    redisSupport.removeValue(key);
    return ResponseEntity.ok().build();
  }
  
  @DeleteMapping("/v1/redis/hash/{key}/{hashKey}")
  public ResponseEntity<?> hdel(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey) {
    redisSupport.removeHash(key, hashKey);
    return ResponseEntity.ok().build();
  }
}