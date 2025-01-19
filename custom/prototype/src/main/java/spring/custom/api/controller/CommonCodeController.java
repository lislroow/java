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
import spring.custom.api.vo.CommonCodeVo.AllCodeVo;

@RestController
@RequiredArgsConstructor
public class CommonCodeController {

  final ModelMapper modelMapper;
  final CommonCodeDao commonCodeDao;
  
  @GetMapping("/v1/common/codes/all")
  @Cacheable(value = "cache:common-code:all")
  public List<CommonCodeDto.AllCodeRes> allCodes() {
    List<CommonCodeVo.AllCodeVo> result = commonCodeDao.allCodes();
    
    List<CommonCodeDto.AllCodeRes> resDto = result.stream()
        .collect(Collectors.groupingBy(AllCodeVo::getCdGrp))
        .entrySet().stream()
        .map(entry -> {
          String key = entry.getKey();
          List<CommonCodeDto.CodeRes> value = entry.getValue().stream()
              .map(vo -> modelMapper.map(vo, CommonCodeDto.CodeRes.class))
              .toList();
          return new CommonCodeDto.AllCodeRes(key, value);
        })
        .collect(Collectors.toList());
    return resDto;
  }
  
  @GetMapping("/v1/common/codes/{cdGrp}")
  public List<CommonCodeDto.CodeRes> findCodesByCdGrp(
      @PathVariable String cdGrp) {
    List<CommonCodeVo.CodeVo> result = commonCodeDao.findCodesByCdGrp(cdGrp);
    
    List<CommonCodeDto.CodeRes> resDto = result.stream()
        .map(item -> modelMapper.map(item, CommonCodeDto.CodeRes.class))
        .collect(Collectors.toList());
    return resDto;
  }
  
}
