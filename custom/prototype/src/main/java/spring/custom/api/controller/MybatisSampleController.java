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
  public MybatisSampleResDto.ScientistList allScientists() {
    List<ScientistVo> result = mybatisSampleDao.allScientists();
    
    MybatisSampleResDto.ScientistList resDto = new MybatisSampleResDto.ScientistList(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisSampleResDto.Scientist.class))
          .collect(Collectors.toList()));
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientists")
  public PageResponse<MybatisSampleResDto.Scientist> findScientists(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    PageResponse<ScientistVo> result = mybatisSampleDao.findScientists(PageRequest.of(page, size));
    
    PageResponse<MybatisSampleResDto.Scientist> resDto = new PageResponse<MybatisSampleResDto.Scientist>(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisSampleResDto.Scientist.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientists/search")
  public PageResponse<MybatisSampleResDto.Scientist> searchScientists(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String fosCd,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    ScientistVo.SearchVo searchVo = ScientistVo.SearchVo.builder()
        .name(name)
        .fosCd(fosCd)
        .build();
    PageResponse<ScientistVo> result = mybatisSampleDao.searchScientists(PageRequest.of(page, size), searchVo);
    
    PageResponse<MybatisSampleResDto.Scientist> resDto = new PageResponse<MybatisSampleResDto.Scientist>(
        result.stream()
          .map(item -> modelMapper.map(item, MybatisSampleResDto.Scientist.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientist/{id}")
  public ResponseEntity<MybatisSampleResDto.Scientist> findScientistById(
      @PathVariable Integer id) {
    ScientistVo result = mybatisSampleDao.findScientistById(id);
    MybatisSampleResDto.Scientist resDto = modelMapper.map(result, MybatisSampleResDto.Scientist.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/mybatis-sample/scientist")
  public ResponseEntity<?> addScientist(
      @RequestBody MybatisSampleReqDto.AddScientist reqDto) {
    ScientistVo.AddVo addVo = modelMapper.map(reqDto, ScientistVo.AddVo.class);
    mybatisSampleService.addScientist(addVo);
    
    return ResponseEntity.status(HttpStatus.CREATED).build(); // location 정보는 JPA 일 때 적합
  }
  
  @PutMapping("/v1/mybatis-sample/scientist")
  public ResponseEntity<?> modifyScientistById(
      @RequestBody MybatisSampleReqDto.ModifyScientist reqDto) {
    ScientistVo.ModifyVo modifyVo = modelMapper.map(reqDto, ScientistVo.ModifyVo.class);
    int result = mybatisSampleService.modifyScientistById(modifyVo);
    
    if (result == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(modifyVo);
    }
  }
  
  @DeleteMapping("/v1/mybatis-sample/scientist/{id}")
  public ResponseEntity<?> removeScientistById(
      @PathVariable Integer id) {
    mybatisSampleService.removeScientistById(id);
    
    return ResponseEntity.noContent().build();
  }
  
}
