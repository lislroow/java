package spring.sample.app.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dto.ReqCardDto;
import spring.sample.app.dto.ResCardDto;
import spring.sample.app.service.CardUsageService;
import spring.sample.app.vo.CardUsageVo;
import spring.sample.common.annotation.NonSecure;

@Slf4j
@Api(tags = { "Card API" })
@RestController
@RequiredArgsConstructor
public class CardUsageController {

  private final ModelMapper modelMapper;
  private final CardUsageService cardUsageService;

  @ApiOperation(value = "", notes = "")
  @ApiImplicitParams({
    //@ApiImplicitParam(name = "", value = "", dataType = "String", paramType = "header", required = true),
    //@ApiImplicitParam(name="", value = "", paramType = "header", required = true),
    //@ApiImplicitParam(name="", value = "", paramType = "header", required = true),
    //@ApiImplicitParam(name="", value = "", paramType = "header", required = true)
  })
  @PostMapping("/usage")
  @NonSecure
  public ResCardDto.UsageList getCardUsageList(@Valid @RequestBody ReqCardDto.UsageSearch request) {
    PageInfo<CardUsageVo> pageInfo = new PageInfo<>(cardUsageService.getCardUsageList(request));
    ResCardDto.UsageList resDto = new ResCardDto.UsageList();
    resDto.setList(pageInfo.getList().stream().map(data -> modelMapper.map(data, ResCardDto.UsageList.SubData.class)).collect(Collectors.toList()));
    resDto.setTotCnt(pageInfo.getTotal());
    resDto.setPageNum(pageInfo.getPageNum());
    resDto.setPageSize(pageInfo.getPageSize());
    return resDto;
  }
  
}
