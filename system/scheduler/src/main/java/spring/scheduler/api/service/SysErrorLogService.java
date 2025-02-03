package spring.scheduler.api.service;

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
  
  public SysErrorLogEntity addSysErrorLog(SysErrorLogDto.RedisDto dto) {
    SysErrorLogEntity entity = modelMapper.map(dto, SysErrorLogEntity.class);
    return sysErrorLogRepository.save(entity);
  }
  
}
