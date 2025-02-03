package spring.scheduler.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.dto.SysErrorLogDto;
import spring.scheduler.api.entity.SysErrorLogEntity;
import spring.scheduler.api.entity.repository.SysErrorLogRepository;

@RestController
@RequiredArgsConstructor
public class SysErrorLogController {

  final ModelMapper modelMapper;
  @Nullable final SysErrorLogRepository sysErrorLogRepository;

  @GetMapping("/v1/sys-error-log/search")
  public Page<SysErrorLogDto.SysErrorLogRes> searchSysErrorLogs(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Page<SysErrorLogEntity> result = sysErrorLogRepository.findAll(PageRequest.of(page, size));
    return result.map(planet -> modelMapper.map(planet, SysErrorLogDto.SysErrorLogRes.class));
  }
  
}
