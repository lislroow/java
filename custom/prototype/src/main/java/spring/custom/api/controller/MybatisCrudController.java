package spring.custom.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.MybatisCrudDao;
import spring.custom.api.dto.MybatisCrudReqDto;
import spring.custom.api.dto.MybatisCrudResDto;
import spring.custom.api.service.MybatisCrudService;
import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedData;

@RestController
@RequiredArgsConstructor
public class MybatisCrudController {

  final ModelMapper modelMapper;
  final MybatisCrudService scientistService;
  final MybatisCrudDao scientistDao;
  
  @GetMapping("/v1/mybatis-crud/scientists/all")
  public MybatisCrudResDto.ScientistList findAll() {
    
    List<ScientistVo> result = scientistDao.findAll();
    
    MybatisCrudResDto.ScientistList resDto = new MybatisCrudResDto.ScientistList(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisCrudResDto.Scientist.class))
          .collect(Collectors.toList()));
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-crud/scientists")
  public PagedData<MybatisCrudResDto.Scientist> findList(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    
    PagedData<ScientistVo> result = scientistDao.findList(PageRequest.of(page, size));
    
    PagedData<MybatisCrudResDto.Scientist> resDto = new PagedData<MybatisCrudResDto.Scientist>(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisCrudResDto.Scientist.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-crud/scientists/search")
  public PagedData<MybatisCrudResDto.Scientist> searchByName(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    
    ScientistVo.FindVo vo = ScientistVo.FindVo.builder()
        .name(name)
        .build();
    PagedData<ScientistVo> result = scientistDao.findListByName(PageRequest.of(page, size), vo);
    
    PagedData<MybatisCrudResDto.Scientist> resDto = new PagedData<MybatisCrudResDto.Scientist>(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisCrudResDto.Scientist.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-crud/scientist/{id}")
  public ResponseEntity<MybatisCrudResDto.Scientist> findById(
      @PathVariable Integer id) {
    
    ScientistVo result = scientistDao.findById(id);
    MybatisCrudResDto.Scientist resDto = modelMapper.map(result, MybatisCrudResDto.Scientist.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/mybatis-crud/scientist")
  public ResponseEntity<?> modifyNameById(
      @RequestBody MybatisCrudReqDto.ModifyDto reqDto) {
    
    ScientistVo.ModifyVo vo = modelMapper.map(reqDto, ScientistVo.ModifyVo.class);
    int result = scientistService.modifyNameById(vo);
    
    if (result == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(vo);
    }
  }
  
  @PutMapping("/v1/mybatis-crud/scientist")
  public ResponseEntity<?> add(
      @RequestBody MybatisCrudReqDto.AddDto reqDto) {
    
    ScientistVo.AddVo vo = modelMapper.map(reqDto, ScientistVo.AddVo.class);
    scientistService.add(vo);
    
    return ResponseEntity.status(HttpStatus.CREATED).build(); // location 정보는 JPA 일 때 적합
  }
  
  @DeleteMapping("/v1/mybatis-crud/scientist/{id}")
  public ResponseEntity<?> removeById(
      @PathVariable Integer id) {
    
    ScientistVo.RemoveVo vo = ScientistVo.RemoveVo.builder()
        .id(id)
        .build();
    
    scientistService.removeById(vo);
    
    return ResponseEntity.noContent().build();
  }
  
}
