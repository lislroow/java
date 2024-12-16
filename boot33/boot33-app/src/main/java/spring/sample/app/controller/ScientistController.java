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
import spring.sample.app.dao.ScientistDao;
import spring.sample.app.dto.ScientistReqDto;
import spring.sample.app.dto.ScientistResDto;
import spring.sample.app.service.ScientistService;
import spring.sample.app.vo.ScientistVo;
import spring.sample.common.dto.ResponseDto;
import spring.sample.common.mybatis.Pageable;
import spring.sample.common.mybatis.Paged;
import spring.sample.common.mybatis.PagedList;

@RestController
@RequiredArgsConstructor
public class ScientistController {

  final ModelMapper modelMapper;
  final ScientistService scientistService;
  final ScientistDao scientistDao;
  
  @GetMapping("/v1/scientists")
  public ResponseDto<ScientistResDto.ScientistList> findAll() {
    
    List<ScientistVo> result = scientistDao.findAll();
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/scientist/{id}")
  public ResponseDto<ScientistResDto.Scientist> findById(
      @PathVariable String id) {
    
    ScientistVo result = scientistDao.findById(id);
    ScientistResDto.Scientist resDto = modelMapper.map(result, ScientistResDto.Scientist.class);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/scientist")
  public ResponseDto<ScientistResDto.PagedScientistList> findList(
      Pageable pageable) {
    
    PagedList<ScientistVo> result = scientistDao.findList(pageable);
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    Paged paged = modelMapper.map(result, Paged.class);
    ScientistResDto.PagedScientistList resDto = new ScientistResDto.PagedScientistList();
    resDto.setPaged(paged);
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/scientist/list/{name}")
  public PagedList<ScientistVo> findListByName(
      @PathVariable String name, Pageable pageable) {
    
    ScientistVo.FindVo vo = ScientistVo.FindVo.builder()
        .name(name)
        .build();
    vo.setPageable(pageable);
    PagedList<ScientistVo> result = scientistDao.findListByName(vo);
    return result;
  }
  
  @PostMapping("/v1/scientist")
  public ResponseDto<ScientistResDto.Scientist> add(
      @RequestBody ScientistReqDto.AddDto reqDto) {
    
    ScientistVo.AddVo vo = modelMapper.map(reqDto, ScientistVo.AddVo.class);
    ScientistVo result = scientistService.add(vo);
    ScientistResDto.Scientist resDto = modelMapper.map(result, ScientistResDto.Scientist.class);
    return ResponseDto.body(resDto);
  }
  
  @PutMapping("/v1/scientist")
  public ResponseDto<?> modifyNameById(
      @RequestBody ScientistReqDto.ModifyDto reqDto) {
    
    ScientistVo.ModifyVo vo = modelMapper.map(reqDto, ScientistVo.ModifyVo.class);
    scientistService.modifyNameById(vo);
    return ResponseDto.body();
  }
  
  @DeleteMapping("/v1/scientist/{id}")
  public ResponseDto<?> removeById(
      @PathVariable String id) {
    
    ScientistVo.RemoveVo vo = ScientistVo.RemoveVo.builder()
        .id(id)
        .build();
    scientistService.removeById(vo);
    return ResponseDto.body();
  }
  
}
