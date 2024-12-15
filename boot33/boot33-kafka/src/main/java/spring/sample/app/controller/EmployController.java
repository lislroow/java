package spring.sample.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.app.dao.EmployDao;
import spring.sample.app.dto.EmployReqDto;
import spring.sample.app.dto.EmployResDto;
import spring.sample.app.service.EmployService;
import spring.sample.app.vo.EmployVo;
import spring.sample.common.constant.Constant;
import spring.sample.common.dto.ResponseDto;
import spring.sample.common.mybatis.Pageable;
import spring.sample.common.mybatis.Paged;
import spring.sample.common.mybatis.PagedList;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constant.APP.BASE_URI)
public class EmployController {

  final ModelMapper modelMapper;
  final EmployService employService;
  final EmployDao employDao;
  
  @GetMapping("/v1/employees")
  public ResponseDto<EmployResDto.EmployList> findAll() {
    
    List<EmployVo> result = employDao.findAll();
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/employ/{id}")
  public ResponseDto<EmployResDto.Employ> findById(
      @PathVariable String id) {
    
    EmployVo result = employDao.findById(id);
    EmployResDto.Employ resDto = modelMapper.map(result, EmployResDto.Employ.class);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/employ")
  public ResponseDto<EmployResDto.PagedEmployList> findList(
      Pageable pageable) {
    
    PagedList<EmployVo> result = employDao.findList(pageable);
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    Paged paged = modelMapper.map(result, Paged.class);
    EmployResDto.PagedEmployList resDto = new EmployResDto.PagedEmployList();
    resDto.setPaged(paged);
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/employ/list/{name}")
  public PagedList<EmployVo> findListByName(
      @PathVariable String name, Pageable pageable) {
    
    EmployVo.FindVo vo = EmployVo.FindVo.builder()
        .name(name)
        .build();
    vo.setPageable(pageable);
    PagedList<EmployVo> result = employDao.findListByName(vo);
    return result;
  }
  
  @PostMapping("/v1/employ")
  public ResponseDto<EmployResDto.Employ> add(
      @RequestBody EmployReqDto.AddDto reqDto) {
    
    EmployVo.AddVo vo = modelMapper.map(reqDto, EmployVo.AddVo.class);
    EmployVo result = employService.add(vo);
    EmployResDto.Employ resDto = modelMapper.map(result, EmployResDto.Employ.class);
    return ResponseDto.body(resDto);
  }
  
  @PutMapping("/v1/employ")
  public ResponseDto<?> modifyNameById(
      @RequestBody EmployReqDto.ModifyDto reqDto) {
    
    EmployVo.ModifyVo vo = modelMapper.map(reqDto, EmployVo.ModifyVo.class);
    employService.modifyNameById(vo);
    return ResponseDto.body();
  }
  
  @DeleteMapping("/v1/employ/{id}")
  public ResponseDto<?> removeById(
      @PathVariable String id) {
    
    EmployVo.RemoveVo vo = EmployVo.RemoveVo.builder()
        .id(id)
        .build();
    employService.removeById(vo);
    return ResponseDto.body();
  }
  
}
