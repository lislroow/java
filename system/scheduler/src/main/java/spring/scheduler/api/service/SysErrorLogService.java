package spring.scheduler.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.custom.dto.SysErrorLogDto;
import spring.scheduler.api.entity.SysErrorLogEntity;
import spring.scheduler.api.entity.repository.SysErrorLogRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SysErrorLogService {

  final ModelMapper modelMapper;
  @Nullable final SysErrorLogRepository sysErrorLogRepository;
  
  public List<SysErrorLogEntity> addSysErrorLogs(List<SysErrorLogDto.RedisDto> dtoList) {
    List<SysErrorLogEntity> entityList = dtoList.stream()
        .map(item -> modelMapper.map(item, SysErrorLogEntity.class))
        .collect(Collectors.toList());
    sysErrorLogRepository.saveAll(entityList);
    return entityList;
  }
  
}
