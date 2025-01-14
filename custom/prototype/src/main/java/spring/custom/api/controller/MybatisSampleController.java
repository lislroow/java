package spring.custom.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
import spring.custom.api.dao.MybatisSampleDao;
import spring.custom.api.dto.MybatisSampleReqDto;
import spring.custom.api.dto.MybatisSampleResDto;
import spring.custom.api.service.MybatisSampleService;
import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@RestController
@RequiredArgsConstructor
public class MybatisSampleController {

  final ModelMapper modelMapper;
  final MybatisSampleService mybatisSampleService;
  final MybatisSampleDao mybatisSampleDao;
  
  @GetMapping("/v1/mybatis-sample/scientists/all")
  public MybatisSampleResDto.ScientistList findAll() {
    
    List<ScientistVo> result = mybatisSampleDao.findAll();
    
    MybatisSampleResDto.ScientistList resDto = new MybatisSampleResDto.ScientistList(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisSampleResDto.Scientist.class))
          .collect(Collectors.toList()));
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientists")
  public PageResponse<MybatisSampleResDto.Scientist> findList(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    
    PageResponse<ScientistVo> result = mybatisSampleDao.findList(PageRequest.of(page, size));
    
    PageResponse<MybatisSampleResDto.Scientist> resDto = new PageResponse<MybatisSampleResDto.Scientist>(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisSampleResDto.Scientist.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientists/search")
  public PageResponse<MybatisSampleResDto.Scientist> searchByName(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    
    ScientistVo.FindVo vo = ScientistVo.FindVo.builder()
        .name(name)
        .build();
    PageResponse<ScientistVo> result = mybatisSampleDao.findListByName(PageRequest.of(page, size), vo);
    
    PageResponse<MybatisSampleResDto.Scientist> resDto = new PageResponse<MybatisSampleResDto.Scientist>(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisSampleResDto.Scientist.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientist/{id}")
  public ResponseEntity<MybatisSampleResDto.Scientist> findById(
      @PathVariable Integer id) {
    
    ScientistVo result = mybatisSampleDao.findById(id);
    MybatisSampleResDto.Scientist resDto = modelMapper.map(result, MybatisSampleResDto.Scientist.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/mybatis-sample/scientist")
  public ResponseEntity<?> modifyNameById(
      @RequestBody MybatisSampleReqDto.ModifyScientist reqDto) {
    
    ScientistVo.ModifyVo vo = modelMapper.map(reqDto, ScientistVo.ModifyVo.class);
    int result = mybatisSampleService.modifyNameById(vo);
    
    if (result == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(vo);
    }
  }
  
  @PutMapping("/v1/mybatis-sample/scientist")
  public ResponseEntity<?> add(
      @RequestBody MybatisSampleReqDto.AddScientist reqDto) {
    
    ScientistVo.AddVo vo = modelMapper.map(reqDto, ScientistVo.AddVo.class);
    mybatisSampleService.add(vo);
    
    return ResponseEntity.status(HttpStatus.CREATED).build(); // location 정보는 JPA 일 때 적합
  }
  
  @DeleteMapping("/v1/mybatis-sample/scientist/{id}")
  public ResponseEntity<?> removeById(
      @PathVariable Integer id) {
    
    ScientistVo.RemoveVo vo = ScientistVo.RemoveVo.builder()
        .id(id)
        .build();
    
    mybatisSampleService.removeById(vo);
    
    return ResponseEntity.noContent().build();
  }
  
}
