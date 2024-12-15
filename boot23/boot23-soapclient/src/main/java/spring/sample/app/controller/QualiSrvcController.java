package spring.sample.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dao.QualiSrvcDao;
import spring.sample.app.dto.ReqQualiSrvcDto;
import spring.sample.app.dto.ResQualiSrvcDto;
import spring.sample.app.service.QualiSrvcService;
import spring.sample.app.vo.QualiSrvcDataVo;
import spring.sample.app.vo.QualiSrvcVo;
import spring.sample.common.constant.Constant;
import spring.sample.common.dto.ResponseDto;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = Constant.APP.BASE_URI)
public class QualiSrvcController {
  
  private final ModelMapper modelMapper;
  
  private final QualiSrvcDao dao;
  private final QualiSrvcService service;
  
  @PutMapping("/soapclient/v1/quali/srvc")
  public void saveSrvc(@RequestBody ReqQualiSrvcDto.Srvc request) {
    QualiSrvcVo result = modelMapper.map(request, QualiSrvcVo.class);
    service.saveSrvc(result);
  }
  
  @GetMapping("/soapclient/v1/quali/srvc/{id}")
  public ResponseDto<ResQualiSrvcDto.Srvc> searchSrvcList(@PathVariable String id) {
    ResQualiSrvcDto.Srvc resDto = dao.selectById(id)
        .map(result -> modelMapper.map(result, ResQualiSrvcDto.Srvc.class))
        .orElse(null);
    return ResponseDto.body(resDto);
  }
  
  @PostMapping("/soapclient/v1/quali/srvc")
  public ResponseDto<ResQualiSrvcDto.SrvcList> searchSrvcList(
      @RequestBody ReqQualiSrvcDto.SrvcSearch request) {
    
    String srvcName = request.getSrvcName();
    String id = request.getId();
    List<QualiSrvcVo> vo = dao.select(srvcName, id);
    PageInfo<QualiSrvcVo> pageInfo = new PageInfo<>(vo);
    ResQualiSrvcDto.SrvcList resDto = new ResQualiSrvcDto.SrvcList();
    resDto.setList(pageInfo.getList().stream()
        .map(data -> modelMapper.map(data, ResQualiSrvcDto.SrvcList.SubData.class))
        .collect(Collectors.toList()));
    resDto.setTotCnt(pageInfo.getTotal());
    resDto.setPageNum(pageInfo.getPageNum());
    resDto.setPageSize(pageInfo.getPageSize());
    return ResponseDto.body(resDto);
  }
  
  @PutMapping("/soapclient/v1/quali/srvc/data")
  public void saveSrvcData(@RequestBody ReqQualiSrvcDto.SrvcData request) {
    QualiSrvcDataVo vo = modelMapper.map(request, QualiSrvcDataVo.class);
    service.saveSrvcData(vo);
  }
  
  @GetMapping("/soapclient/v1/quali/srvc/data/{qvServiceId}")
  public ResponseDto<ResQualiSrvcDto.SrvcDataList> selectSrvc(
      @PathVariable String qvServiceId) {
    
    List<QualiSrvcDataVo> result = dao.selectDataBySrvcId(qvServiceId);
    List<ResQualiSrvcDto.SrvcDataList.SubData> list = result.stream()
        .map(data -> modelMapper.map(data, ResQualiSrvcDto.SrvcDataList.SubData.class))
        .collect(Collectors.toList());
    ResQualiSrvcDto.SrvcDataList resDto = new ResQualiSrvcDto.SrvcDataList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
}
