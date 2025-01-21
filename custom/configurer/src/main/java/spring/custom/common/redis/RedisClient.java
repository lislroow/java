package spring.custom.common.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RedisClient {

  final RedisTemplate<String, String> redisTemplate;
  final ModelMapper modelMapper;
  final ObjectMapper objectMapper;
  
  
  // key
  public Set<String> keys() {
    return this.redisTemplate.keys("*");
  }
  
  public Set<String> keys(String key) {
    return this.redisTemplate.keys(key);
  }
  
  public Set<Object> hkeys(String key) {
    Set<Object> hkeySet = this.redisTemplate.opsForHash().keys(key);
    return hkeySet;
  }
  
  // value: opsForValue()
  public void setValue(String key, String val) {
    this.redisTemplate.opsForValue().set(key, val);
  }
  
  public void setList(String key, List<String> list) throws JsonProcessingException {
    String val = objectMapper.writeValueAsString(list);
    this.setValue(key, val);
  }
  
  public void setValue(String key, String val, Duration ttl) {
    this.redisTemplate.opsForValue().set(key, val, ttl);
  }
  
  public String getValue(String key) {
    return this.redisTemplate.opsForValue().get(key);
  }
  
  public String getValueAndDelete(String key) {
    return this.redisTemplate.opsForValue().getAndDelete(key);
  }
  
  public List<String> getList(String key) throws JsonProcessingException {
    String val = this.getValue(key);
    List<String> list = Arrays.asList(objectMapper.readValue(val, String[].class));
    return list;
  }
  
  public void removeValue(String key) {
    this.redisTemplate.delete(key);
  }
  
  // hash: opsForHash()
  public void setHash(String key, String hashKey, Object val) {
    this.redisTemplate.opsForHash().put(key, hashKey, val);
  }
  
  public void setHash(String key, String hashKey, Object val, Duration ttl) {
    this.redisTemplate.opsForHash().put(key, hashKey, val);
    this.redisTemplate.expire(key, ttl);
  }
  
  @SuppressWarnings("unchecked")
  public <T> T getHash(String key, String hashKey) {
    return (T) this.redisTemplate.opsForHash().get(key, hashKey);
  }
  
  public Map<String, String> getHash(String key) {
    Map<String, String> result = redisTemplate.opsForHash().entries(key)
        .entrySet().stream()
        .collect(Collectors.toMap(
            entry -> String.valueOf(entry.getKey()),
            entry -> String.valueOf(entry.getValue())
        ));
    return result;
  }
  
  public <T> List<T> getHashAll(String key, Class<T> type) {
    List<T> list = new ArrayList<>();
    for (Object object : this.redisTemplate.opsForHash().values(key)) {
      if (object == null) {
        list.add(null);
      } else {
        list.add(modelMapper.map(object, type));
      }
    }
    return list;
  }
  
  public boolean isHashEmpty(String key, String hashKey) {
    return this.redisTemplate.opsForHash().hasKey(key, hashKey);
  }
  
  public void removeHash(String key, String hashKey) {
    this.redisTemplate.opsForHash().delete(key, hashKey);
  }
  
}
