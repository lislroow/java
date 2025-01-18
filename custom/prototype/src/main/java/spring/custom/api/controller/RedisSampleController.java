package spring.custom.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dto.RedisSampleDto;
import spring.custom.api.redis.RedisSampleRepository;
import spring.custom.api.service.RedisSampleService;
import spring.custom.api.vo.RedisSampleVo;
import spring.custom.common.constant.Constant;
import spring.custom.common.redis.RedisSupport;

@RestController
@ConditionalOnProperty(prefix = "spring.data.redis", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class RedisSampleController {
  
  final ModelMapper modelMapper;
  final RedisSampleService redisSampleService;
  final RedisSampleRepository redisSampleRepository;
  final RedisSupport redisSupport;
  
  @PutMapping("/v1/redis-sample/item")
  public ResponseEntity<?> addSample(@RequestBody RedisSampleDto.RedisAddReq reqDto) {
    RedisSampleVo param = modelMapper.map(reqDto, RedisSampleVo.class);
    redisSampleService.addSample(param);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
  
  @GetMapping("/v1/redis-sample/item/{id}")
  public ResponseEntity<RedisSampleDto.RedisRes> findById(@PathVariable Integer id) {
    Optional<RedisSampleVo> result = redisSampleRepository.findById(id);
    if (result.isPresent()) {
      RedisSampleDto.RedisRes resDto = modelMapper.map(result.get(), RedisSampleDto.RedisRes.class);
      return ResponseEntity.ok(resDto);
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
  }
  
  @GetMapping("/v1/redis-sample/item/all")
  public ResponseEntity<RedisSampleDto.RedisListRes> findAll() {
    Iterable<RedisSampleVo> result = redisSampleRepository.findAll();
    
    List<RedisSampleDto.RedisRes> list = StreamSupport.stream(result.spliterator(), true)
        .map(item -> modelMapper.map(item, RedisSampleDto.RedisRes.class))
        .collect(Collectors.toList());
    if (!list.isEmpty()) {
      RedisSampleDto.RedisListRes resDto = new RedisSampleDto.RedisListRes();
      resDto.setList(list);
      return ResponseEntity.ok(resDto);
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
  }
  
  @GetMapping("/v1/redis-sample/item/search")
  public ResponseEntity<RedisSampleDto.RedisListRes> searchByName(
      @RequestParam(required = false) String name) {
    Iterable<RedisSampleVo> result = redisSampleRepository.findAll();
    
    List<RedisSampleDto.RedisRes> list = StreamSupport.stream(result.spliterator(), true)
        .filter(item -> name == null || item.getName().indexOf(name) > -1)
        .map(item -> modelMapper.map(item, RedisSampleDto.RedisRes.class))
        .collect(Collectors.toList());
    if (!list.isEmpty()) {
      RedisSampleDto.RedisListRes resDto = new RedisSampleDto.RedisListRes();
      resDto.setList(list);
      return ResponseEntity.ok(resDto);
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
  }
  
  // ---

  //final RedisTemplate<String, Object> redisTemplate;
  
  @GetMapping("/v1/redis-sample/keys")
  public ResponseEntity<Set<String>> keys() {
    Set<String> keys = redisSupport.keys();
    return ResponseEntity.ok(keys);
  }
  
  @GetMapping("/v1/redis-sample/key/{key}")
  public ResponseEntity<Set<String>> key(@PathVariable("key") String key) {
    Set<String> keys = redisSupport.keys(key);
    return ResponseEntity.ok(keys);
  }
  
  @GetMapping("/v1/redis-sample/value/{key}")
  public ResponseEntity<String> get(@PathVariable("key") String key) {
    String value = redisSupport.getValue(key);
    return ResponseEntity.ok(value);
  }
  
  @GetMapping("/v1/redis-sample/hash/{key}/{hashKey}")
  public ResponseEntity<String> hget(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey) {
    String hashValue = redisSupport.getHash(key, hashKey);
    return ResponseEntity.ok(hashValue);
  }
  
  @PutMapping("/v1/redis-sample/value/{key}")
  public ResponseEntity<String> set(@PathVariable("key") String key, @RequestParam("value") String value) {
    redisSupport.setValue(key, value);
    value = redisSupport.getValue(key);
    return ResponseEntity.ok(value);
  }
  
  @PutMapping("/v1/redis-sample/hash/{key}/{hashKey}")
  public ResponseEntity<String> hset(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey,
      @RequestParam("value") String value) {
    redisSupport.setHash(key, hashKey, value);
    String hashValue = redisSupport.getHash(key, hashKey);
    return ResponseEntity.ok(hashValue);
  }
  
  @DeleteMapping("/v1/redis-sample/value/{key}")
  public ResponseEntity<?> del(@PathVariable("key") String key) {
    redisSupport.removeValue(key);
    return ResponseEntity.ok().build();
  }
  
  @DeleteMapping("/v1/redis-sample/hash/{key}/{hashKey}")
  public ResponseEntity<?> hdel(@PathVariable("key") String key, @PathVariable("hashKey") String hashKey) {
    redisSupport.removeHash(key, hashKey);
    return ResponseEntity.ok().build();
  }
}
