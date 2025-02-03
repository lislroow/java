package spring.scheduler.api.schedule;

import java.util.ArrayList;
import java.util.List;

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
    String json = null;
    
    List<SysErrorLogDto.RedisDto> dtoList = null;
    while ((json = listOps.leftPop(key)) != null) {
      if (dtoList == null) {
        dtoList = new ArrayList<>();
      }
      try {
        dtoList.add(objectMapper.readValue(json, SysErrorLogDto.RedisDto.class));
      } catch (JsonProcessingException e) {
        log.error("{}\njson: {}", e.getStackTrace()[0], json);
      }
    }
    if (dtoList == null || dtoList.size() == 0) {
      return;
    }
    sysErrorLogService.addSysErrorLogs(dtoList);
  }
  
}
