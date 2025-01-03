package spring.custom.common.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RedisSupport {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ModelMapper modelMapper;
  
  // key
  public Set<String> keys() {
    return this.redisTemplate.keys("*");
  }
  
  public Set<String> key(String key) {
    return this.redisTemplate.keys(key);
  }
  
  // value: opsForValue()
  public void setValue(String key, Object val) {
    this.redisTemplate.opsForValue().set(key, val);
  }
  
  public void setValue(String key, Object val, Duration ttl) {
    this.redisTemplate.opsForValue().set(key, val, ttl);
  }
  
  public <T> T getValue(String key) {
    return (T) this.redisTemplate.opsForValue().get(key);
  }
  
  public void delValue(String key) {
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
  
  public void deleteHash(String key, String hashKey) {
    if (hashKey != null && hashKey.indexOf("*") > -1) {
      Set<Object> hkeySet = this.redisTemplate.opsForHash().keys(key);
      hkeySet.forEach(hkey -> this.redisTemplate.opsForHash().delete(key, hkey));
    } else {
      this.redisTemplate.opsForHash().delete(key, hashKey);
    }
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
