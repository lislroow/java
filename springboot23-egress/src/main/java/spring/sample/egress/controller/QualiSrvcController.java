package spring.sample.egress.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.egress.dao.QualiSrvcDao;
import spring.sample.egress.dto.ReqQualiSrvcDto;
import spring.sample.egress.dto.ResQualiSrvcDto;
import spring.sample.egress.service.QualiSrvcService;
import spring.sample.egress.vo.QualiSrvcDataVo;
import spring.sample.egress.vo.QualiSrvcVo;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class QualiSrvcController {
  
  private final ModelMapper modelMapper;
  
  private final QualiSrvcDao dao;
  private final QualiSrvcService service;
  
  @PutMapping("/egress/v1/quali/srvc")
  public void saveSrvc(@RequestBody ReqQualiSrvcDto.Srvc req) {
    QualiSrvcVo vo = modelMapper.map(req, QualiSrvcVo.class);
    service.saveSrvc(vo);
  }
  
  @GetMapping("/egress/v1/quali/srvc/{id}")
  public ResQualiSrvcDto.Srvc searchSrvcList(@PathVariable String id) {
    QualiSrvcVo vo = dao.selectById(id);
    ResQualiSrvcDto.Srvc resDto = null;
    if (vo != null) {
      resDto = modelMapper.map(vo, ResQualiSrvcDto.Srvc.class);
    }
    return resDto;
  }
  
  @PostMapping("/egress/v1/quali/srvc")
  public ResQualiSrvcDto.SrvcList searchSrvcList(@RequestBody ReqQualiSrvcDto.SrvcSearch req) {
    String srvcName = req.getSrvcName();
    String id = req.getId();
    List<QualiSrvcVo> vo = dao.select(srvcName, id);
    PageInfo<QualiSrvcVo> pageInfo = new PageInfo<>(vo);
    ResQualiSrvcDto.SrvcList resDto = new ResQualiSrvcDto.SrvcList();
    resDto.setList(pageInfo.getList().stream()
        .map(data -> modelMapper.map(data, ResQualiSrvcDto.SrvcList.SubData.class))
        .collect(Collectors.toList()));
    resDto.setTotCnt(pageInfo.getTotal());
    resDto.setPageNum(pageInfo.getPageNum());
    resDto.setPageSize(pageInfo.getPageSize());
    return resDto;
  }
  
  @PutMapping("/egress/v1/quali/srvc/data")
  public void saveSrvcData(@RequestBody ReqQualiSrvcDto.SrvcData req) {
    System.out.println(req);
    QualiSrvcDataVo vo = modelMapper.map(req, QualiSrvcDataVo.class);
    service.saveSrvcData(vo);
  }
  
  @GetMapping("/egress/v1/quali/srvc/data/{qvServiceId}")
  public List<ResQualiSrvcDto.SrvcDataList> selectSrvc(@PathVariable String qvServiceId) {
    List<QualiSrvcDataVo> vo = dao.selectDataBySrvcId(qvServiceId);
    List<ResQualiSrvcDto.SrvcDataList> resDto = vo.stream()
        .map(data -> modelMapper.map(data, ResQualiSrvcDto.SrvcDataList.class))
        .collect(Collectors.toList());
    return resDto;
  }
}
