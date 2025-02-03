package spring.scheduler.api.schedule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.redis.RedisClient;
import spring.custom.dto.SysErrorLogDto;
import spring.scheduler.api.service.SysErrorLogService;

@Component
@Slf4j
public class SysErrorLogScheduler {
  
  @Autowired ObjectMapper objectMapper;
  @Autowired ModelMapper modelMapper;
  @Autowired SysErrorLogService sysErrorLogService;
  @Autowired RedisTemplate<String, String> redisTemplate;
  ListOperations<String, String> listOps;
  
  @PostConstruct
  public void init() {
    listOps = redisTemplate.opsForList();
    if (log.isInfoEnabled()) log.info("listOps: {}", listOps);
  }
  
  @Scheduled(fixedDelayString = "${scheduler.sys-error-log.delay-time:3s}", initialDelay = 8_000)
  public void task() {
    String key = RedisClient.LOG_KEY.ERROR_LOG.key();
    String json = listOps.leftPop(key);
    if (json == null) {
      return;
    }
    try {
      SysErrorLogDto.RedisDto dto = objectMapper.readValue(json, SysErrorLogDto.RedisDto.class);
      sysErrorLogService.addSysErrorLog(dto);
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
  }
  
}
