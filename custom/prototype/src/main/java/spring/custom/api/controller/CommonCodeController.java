package spring.custom.api.controller;

import java.util.List;
import java.util.Map;
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
import spring.custom.api.vo.CommonCodeVo.ResultAllCode;
import spring.custom.common.enumcode.EnumCode;
import spring.custom.common.enumcode.EnumMapper;

@RestController
@RequiredArgsConstructor
public class CommonCodeController {

  final ModelMapper modelMapper;
  final CommonCodeDao commonCodeDao;
  final EnumMapper enumMapper;
  
  @GetMapping("/v1/common/code/all")
  @Cacheable(value = "cache:common-code:all")
  public List<CommonCodeDto.AllCodeRes> allCode() {
    List<CommonCodeVo.ResultAllCode> resultVo = commonCodeDao.allCodes();
    List<CommonCodeDto.AllCodeRes> resDto = resultVo.stream()
        .collect(Collectors.groupingBy(ResultAllCode::getCdGrp))
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
  
  @GetMapping("/v1/common/code/{cdGrp}")
  public List<CommonCodeDto.CodeRes> findCodeByCdGrp(@PathVariable String cdGrp) {
    List<CommonCodeVo.ResultCode> resultVo = commonCodeDao.findCodesByCdGrp(cdGrp);
    List<CommonCodeDto.CodeRes> resDto = resultVo.stream()
        .map(item -> modelMapper.map(item, CommonCodeDto.CodeRes.class))
        .collect(Collectors.toList());
    return resDto;
  }
  
  @GetMapping("/v1/common/code-enum/all")
  public List<CommonCodeDto.AllCodeRes> allCodeEnum() {
    Map<String, List<EnumCode>> allCodes = enumMapper.allCodes();
    List<CommonCodeDto.AllCodeRes> resDto = allCodes.entrySet().stream()
        .map(map -> {
          List<CommonCodeDto.CodeRes> list = map.getValue().stream()
              .map(item -> CommonCodeDto.CodeRes.builder()
                  .cd(item.getValue())
                  .cdNm(item.getLabel())
                  .seq(item.getSeq())
                  .build())
              .collect(Collectors.toList());
          return new CommonCodeDto.AllCodeRes(map.getKey(), list);
        })
        .collect(Collectors.toList());
    return resDto;
  }
  
  @GetMapping("/v1/common/code-enum/{key}")
  public List<CommonCodeDto.CodeRes> findCodeEnumByCdGrp(@PathVariable String key) {
    List<EnumCode> code = enumMapper.getCode(key);
    List<CommonCodeDto.CodeRes> resDto = code.stream()
        .map(item -> CommonCodeDto.CodeRes.builder()
            .cd(item.getValue())
            .cdNm(item.getLabel())
            .seq(item.getSeq())
            .build())
        .collect(Collectors.toList());
    return resDto;
  }
  
  @GetMapping("/v1/common/enums/all")
  public Map<String, List<EnumCode>> allEnums() {
    Map<String, List<EnumCode>> allCode = enumMapper.allCodes();
    return allCode;
  }
  
  @GetMapping("/v1/common/enums/{key}")
  public List<EnumCode> findCodeEnumByKey(@PathVariable String key) {
    List<EnumCode> code = enumMapper.getCode(key);
    return code;
  }
  
}
