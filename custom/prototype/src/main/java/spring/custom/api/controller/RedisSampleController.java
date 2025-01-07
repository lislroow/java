package spring.custom.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dto.RedisSampleReqDto;
import spring.custom.api.service.RedisSampleService;
import spring.custom.api.vo.RedisSampleVo;

@RestController
@RequiredArgsConstructor
public class RedisSampleController {
  
  final ModelMapper modelMapper;
  final RedisSampleService redisSampleService;
  
  @PutMapping("/v1/redis-sample")
  public ResponseEntity<?> addSample(@RequestBody RedisSampleReqDto.Add reqDto) {
    RedisSampleVo param = modelMapper.map(reqDto, RedisSampleVo.class);
    redisSampleService.addSample(param);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
