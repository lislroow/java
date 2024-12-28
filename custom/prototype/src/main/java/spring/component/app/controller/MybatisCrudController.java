package spring.component.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.component.app.dao.MybatisCrudDao;
import spring.component.app.dto.MybatisCrudReqDto;
import spring.component.app.dto.MybatisCrudResDto;
import spring.component.app.service.MybatisCrudService;
import spring.component.app.vo.ScientistVo;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.mybatis.Pageable;
import spring.custom.common.mybatis.Paged;
import spring.custom.common.mybatis.PagedList;

@RestController
@RequiredArgsConstructor
public class MybatisCrudController {

  final ModelMapper modelMapper;
  final MybatisCrudService scientistService;
  final MybatisCrudDao scientistDao;
  
  @GetMapping("/v1/mybatis-crud/scientists")
  public ResponseDto<MybatisCrudResDto.ScientistList> findAll() {
    
    List<ScientistVo> result = scientistDao.findAll();
    MybatisCrudResDto.ScientistList resDto = new MybatisCrudResDto.ScientistList();
    List<MybatisCrudResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisCrudResDto.Scientist.class))
        .collect(Collectors.toList());
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-crud/scientist/{id}")
  public ResponseDto<MybatisCrudResDto.Scientist> findById(
      @PathVariable String id) {
    
    ScientistVo result = scientistDao.findById(id);
    MybatisCrudResDto.Scientist resDto = modelMapper.map(result, MybatisCrudResDto.Scientist.class);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-crud/scientist")
  public ResponseDto<MybatisCrudResDto.PagedScientistList> findList(
      Pageable pageable) {
    
    PagedList<ScientistVo> result = scientistDao.findList(pageable);
    List<MybatisCrudResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisCrudResDto.Scientist.class))
        .collect(Collectors.toList());
    Paged paged = modelMapper.map(result, Paged.class);
    MybatisCrudResDto.PagedScientistList resDto = new MybatisCrudResDto.PagedScientistList();
    resDto.setPaged(paged);
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-crud/scientist/list/{name}")
  public PagedList<ScientistVo> findListByName(
      @PathVariable String name, Pageable pageable) {
    
    ScientistVo.FindVo vo = ScientistVo.FindVo.builder()
        .name(name)
        .build();
    vo.setPageable(pageable);
    PagedList<ScientistVo> result = scientistDao.findListByName(vo);
    return result;
  }
  
  @PostMapping("/v1/mybatis-crud/scientist")
  public ResponseDto<MybatisCrudResDto.Scientist> add(
      @RequestBody MybatisCrudReqDto.AddDto reqDto) {
    
    ScientistVo.AddVo vo = modelMapper.map(reqDto, ScientistVo.AddVo.class);
    scientistService.add(vo);
    return ResponseDto.body();
  }
  
  @PutMapping("/v1/mybatis-crud/scientist")
  public ResponseDto<?> modifyNameById(
      @RequestBody MybatisCrudReqDto.ModifyDto reqDto) {
    
    ScientistVo.ModifyVo vo = modelMapper.map(reqDto, ScientistVo.ModifyVo.class);
    scientistService.modifyNameById(vo);
    return ResponseDto.body();
  }
  
  @DeleteMapping("/v1/mybatis-crud/scientist/{id}")
  public ResponseDto<?> removeById(
      @PathVariable String id) {
    
    ScientistVo.RemoveVo vo = ScientistVo.RemoveVo.builder()
        .id(id)
        .build();
    scientistService.removeById(vo);
    return ResponseDto.body();
  }
  
}
