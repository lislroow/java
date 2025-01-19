package spring.custom.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.CommonCodeDao;
import spring.custom.api.dto.CommonCodeDto;
import spring.custom.api.vo.CommonCodeVo;

@RestController
@RequiredArgsConstructor
public class CommonCodeController {

  final ModelMapper modelMapper;
  final CommonCodeDao commonCodeDao;
  
  @GetMapping("/v1/common-code/codes/all")
  //@Cacheable(value = "cache:common-code", key = "all")
  public List<CommonCodeDto.AllCodeRes> allCodes() {
    List<CommonCodeVo.AllCodeVo> result = commonCodeDao.allCodes();
    
    List<CommonCodeDto.AllCodeRes> resDto = result.stream()
        .map(item -> modelMapper.map(item, CommonCodeDto.AllCodeRes.class))
        .collect(Collectors.toList());
    return resDto;
  }
  
  @GetMapping("/v1/common-code/codes/find/{cdGrp}")
  @Cacheable(value = "cache:common-code", key = "#cdGrp")
  public List<CommonCodeDto.FindCodeRes> findCodesByCdGrp(
      @PathVariable String cdGrp) {
    List<CommonCodeVo.CodeVo> result = commonCodeDao.findCodesByCdGrp(cdGrp);
    
    List<CommonCodeDto.FindCodeRes> resDto = result.stream()
        .map(item -> modelMapper.map(item, CommonCodeDto.FindCodeRes.class))
        .collect(Collectors.toList());
    return resDto;
  }
  
}
