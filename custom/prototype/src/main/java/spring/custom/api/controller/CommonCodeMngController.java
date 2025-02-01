package spring.custom.api.controller;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.CommonCodeMngDao;
import spring.custom.api.dto.CommonCodeMngDto;
import spring.custom.api.vo.CommonCodeMngVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@RestController
@RequiredArgsConstructor
public class CommonCodeMngController {

  final ModelMapper modelMapper;
  @Nullable final CommonCodeMngDao commonCodeMngDao;
  
  @GetMapping("/v1/common/code-mng/code-groups/search")
  public PageResponse<CommonCodeMngDto.CodeGroupRes> searchCodeGroups(
      @RequestParam(required = false) String cdGrp,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    PageResponse<CommonCodeMngVo.ResultCodeGroup> resultVo = commonCodeMngDao.searchCodeGroups(PageRequest.of(page, size), cdGrp);
    
    PageResponse<CommonCodeMngDto.CodeGroupRes> resDto = new PageResponse<CommonCodeMngDto.CodeGroupRes>(
        resultVo.stream()
          .map(item -> modelMapper.map(item, CommonCodeMngDto.CodeGroupRes.class))
          .collect(Collectors.toList())
        , resultVo.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/common/code-mng/codes/search")
  public PageResponse<CommonCodeMngDto.CodeRes> searchCodes(
      @RequestParam(required = false) String cdGrp,
      @RequestParam(required = false) String cdGrpNm,
      @RequestParam(required = false) String cd,
      @RequestParam(required = false) String cdNm,
      @RequestParam(required = false) String useYn,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    CommonCodeMngVo.SearchParam searchVo = CommonCodeMngVo.SearchParam.builder()
        .cdGrp(cdGrp)
        .cdGrpNm(cdGrpNm)
        .cd(cd)
        .cdNm(cdNm)
        .useYn(useYn)
        .build();
    PageResponse<CommonCodeMngVo.ResultCode> resultVo = commonCodeMngDao.searchCodes(PageRequest.of(page, size), searchVo);
    
    PageResponse<CommonCodeMngDto.CodeRes> resDto = new PageResponse<CommonCodeMngDto.CodeRes>(
        resultVo.stream()
          .map(item -> modelMapper.map(item, CommonCodeMngDto.CodeRes.class))
          .collect(Collectors.toList())
        , resultVo.getPageInfo());
    return resDto;
  }
  
}
