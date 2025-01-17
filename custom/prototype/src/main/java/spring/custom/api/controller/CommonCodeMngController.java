package spring.custom.api.controller;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.CommonCodeMngDao;
import spring.custom.api.dto.CommonCodeMngResDto;
import spring.custom.api.vo.CommonCodeMngVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@RestController
@RequiredArgsConstructor
public class CommonCodeMngController {

  final ModelMapper modelMapper;
  final CommonCodeMngDao commonCodeMngDao;
  
  @GetMapping("/v1/common-code/mng/code-groups/search")
  public PageResponse<CommonCodeMngResDto.CodeGroup> searchCodeGroups(
      @RequestParam(required = false) String cdGrp,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    PageResponse<CommonCodeMngVo.CodeGroupVo> result = commonCodeMngDao.searchCodeGroups(PageRequest.of(page, size), cdGrp);
    
    PageResponse<CommonCodeMngResDto.CodeGroup> resDto = new PageResponse<CommonCodeMngResDto.CodeGroup>(
        result.stream()
          .map(item -> modelMapper.map(item, CommonCodeMngResDto.CodeGroup.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/common-code/mng/codes/search")
  public PageResponse<CommonCodeMngResDto.Code> searchCodes(
      @RequestParam(required = false) String cdGrp,
      @RequestParam(required = false) String cdGrpNm,
      @RequestParam(required = false) String cd,
      @RequestParam(required = false) String cdNm,
      @RequestParam(required = false) String useYn,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    CommonCodeMngVo.SearchCodeVo searchVo = CommonCodeMngVo.SearchCodeVo.builder()
        .cdGrp(cdGrp)
        .cdGrpNm(cdGrpNm)
        .cd(cd)
        .cdNm(cdNm)
        .useYn(useYn)
        .build();
    PageResponse<CommonCodeMngVo.CodeVo> result = commonCodeMngDao.searchCodes(PageRequest.of(page, size), searchVo);
    
    PageResponse<CommonCodeMngResDto.Code> resDto = new PageResponse<CommonCodeMngResDto.Code>(
        result.stream()
          .map(item -> modelMapper.map(item, CommonCodeMngResDto.Code.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
}
