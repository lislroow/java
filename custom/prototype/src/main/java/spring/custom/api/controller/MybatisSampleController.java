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

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.MybatisSampleDao;
import spring.custom.api.dto.MybatisSampleDto;
import spring.custom.api.service.MybatisSampleService;
import spring.custom.api.vo.ScientistVo;
import spring.custom.code.EnumScientist;
import spring.custom.common.enumcode.EnumCodeType;
import spring.custom.common.exception.data.DataNotFoundException;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@RestController
@RequiredArgsConstructor
public class MybatisSampleController {

  final ModelMapper modelMapper;
  final MybatisSampleService mybatisSampleService;
  @Nullable final MybatisSampleDao mybatisSampleDao;
  
  @GetMapping("/v1/mybatis-sample/scientists/all")
  public MybatisSampleDto.ScientistListRes allScientists() {
    List<ScientistVo.ResultScientist> resultVo = mybatisSampleDao.allScientists();
    
    MybatisSampleDto.ScientistListRes resDto = new MybatisSampleDto.ScientistListRes(
        resultVo.stream()
          .map(item -> modelMapper.map(item, MybatisSampleDto.ScientistRes.class))
          .collect(Collectors.toList()));
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientists")
  public PageResponse<MybatisSampleDto.ScientistRes> findScientists(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    PageResponse<ScientistVo.ResultScientist> resultVo = mybatisSampleDao.findScientists(PageRequest.of(page, size));
    
    PageResponse<MybatisSampleDto.ScientistRes> resDto = new PageResponse<MybatisSampleDto.ScientistRes>(
        resultVo.stream()
          .map(item -> modelMapper.map(item, MybatisSampleDto.ScientistRes.class))
          .collect(Collectors.toList())
        , resultVo.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientists/search")
  public PageResponse<MybatisSampleDto.ScientistRes> searchScientists(
      @RequestParam(required = false) String name,
      /* dead code */ //@RequestParam(required = false) EnumScientist.FieldOfStudy fosCd,
      @RequestParam(required = false) String fosCd,
      @RequestParam(required = false) @Min(1) @Max(21) @Nullable Integer century,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    ScientistVo.SearchParam searchVo = ScientistVo.SearchParam.builder()
        .name(name)
        .fosCd(EnumCodeType.fromValue(EnumScientist.FieldOfStudy.class, fosCd))
        .century(century)
        .build();
    PageResponse<ScientistVo.ResultScientist> resultVo = mybatisSampleDao.searchScientists(PageRequest.of(page, size), searchVo);
    
    PageResponse<MybatisSampleDto.ScientistRes> resDto = new PageResponse<MybatisSampleDto.ScientistRes>(
        resultVo.stream()
          .map(item -> modelMapper.map(item, MybatisSampleDto.ScientistRes.class))
          .collect(Collectors.toList())
        , resultVo.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/mybatis-sample/scientist/{id}")
  public ResponseEntity<MybatisSampleDto.ScientistRes> findScientistById(
      @PathVariable Integer id) {
    ScientistVo.ResultScientist resultVo = mybatisSampleDao.findScientistById(id)
        .orElseThrow(DataNotFoundException::new);
    MybatisSampleDto.ScientistRes resDto = modelMapper.map(resultVo, MybatisSampleDto.ScientistRes.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/mybatis-sample/scientist")
  public ResponseEntity<?> addScientist(
      @RequestBody MybatisSampleDto.AddScientistReq reqDto) {
    ScientistVo.AddScientist addVo = modelMapper.map(reqDto, ScientistVo.AddScientist.class);
    mybatisSampleService.addScientist(addVo);
    
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
  
  @PutMapping("/v1/mybatis-sample/scientist")
  public ResponseEntity<?> modifyScientistById(
      @RequestBody MybatisSampleDto.ModifyScientistReq reqDto) {
    ScientistVo.ModifyScientist modifyVo = modelMapper.map(reqDto, ScientistVo.ModifyScientist.class);
    int cnt = mybatisSampleService.modifyScientistById(modifyVo);
    
    if (cnt == 0) {
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
