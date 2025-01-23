package spring.custom.api.controller;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
import spring.custom.api.dto.JpaSampleDto;
import spring.custom.api.entity.StarEntity;
import spring.custom.api.entity.repository.StarRepository;
import spring.custom.api.entity.spec.StarSpecification;
import spring.custom.api.service.JpaSampleService;
import spring.custom.common.exception.data.DataNotFoundException;

@RestController
@RequiredArgsConstructor
public class JpaSampleController {

  final ModelMapper modelMapper;
  final JpaSampleService jpaSampleService;
  final StarRepository starRepository;
  
  @GetMapping("/v1/jpa-sample/stars")
  public Page<JpaSampleDto.StarRes> findStars(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    //PageResponse<StarVo.ResultStar> resultVo = jpaSampleDao.findStars(PageRequest.of(page, size));
    //PageResponse<JpaSampleDto.StarRes> resDto = new PageResponse<JpaSampleDto.StarRes>(
    //    resultVo.stream()
    //      .map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class))
    //      .collect(Collectors.toList())
    //    , resultVo.getPageInfo());
    
    Page<StarEntity> result = starRepository.findAll(PageRequest.of(page, size));
    return result.map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class));
  }
  
  @GetMapping("/v1/jpa-sample/stars/search")
  public Page<JpaSampleDto.StarRes> searchStars(
      @RequestParam(required = false) String name,
      //@RequestParam(required = false) Double distance,
      //@RequestParam(required = false) Boolean distanceGreater,
      //@RequestParam(required = false) Integer temperature,
      //@RequestParam(required = false) Boolean temperatureGreater,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    //StarVo.SearchParam searchVo = StarVo.SearchParam.builder()
    //    .name(name)
    //    .fosCd(fosCd)
    //    .build();
    //PageResponse<StarVo.ResultStar> resultVo = jpaSampleDao.searchStars(PageRequest.of(page, size), searchVo);
    //PageResponse<JpaSampleDto.StarRes> resDto = new PageResponse<JpaSampleDto.StarRes>(
    //    resultVo.stream()
    //      .map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class))
    //      .collect(Collectors.toList())
    //    , resultVo.getPageInfo());
    
    Specification<StarEntity> spec = 
        Specification.where(StarSpecification.hasName(name));
    Page<StarEntity> result = starRepository.findAll(spec, PageRequest.of(page, size));
    return result.map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class));
  }
  
  @GetMapping("/v1/jpa-sample/star/{id}")
  public ResponseEntity<JpaSampleDto.StarRes> findStarById(
      @PathVariable Integer id) {
    StarEntity result = starRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
    JpaSampleDto.StarRes resDto = modelMapper.map(result, JpaSampleDto.StarRes.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/jpa-sample/star")
  public ResponseEntity<?> addStar(
      @RequestBody JpaSampleDto.AddStarReq reqDto) {
    //StarVo.AddStar addVo = modelMapper.map(reqDto, StarVo.AddStar.class);
    //jpaSampleService.addStar(addVo);
    
    StarEntity result = jpaSampleService.addStar(reqDto);
    // location
    JpaSampleDto.StarRes resDto = modelMapper.map(result, JpaSampleDto.StarRes.class);
    
    return ResponseEntity.status(HttpStatus.CREATED)
        .location(URI.create("/v1/jpa-sample/star/"+resDto.getId()))
        .body(resDto);
  }
  
  @PutMapping("/v1/jpa-sample/star")
  public ResponseEntity<?> modifyStarById(
      @RequestBody JpaSampleDto.ModifyStarReq reqDto) {
    //StarVo.ModifyStar modifyVo = modelMapper.map(reqDto, StarVo.ModifyStar.class);
    //int cnt = jpaSampleService.modifyStarById(modifyVo);
    
    StarEntity result = jpaSampleService.modifyStarById(reqDto);
    JpaSampleDto.StarRes resDto = result != null 
        ? modelMapper.map(result, JpaSampleDto.StarRes.class)
        : null;
    
    if (result == null) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(resDto);
    }
  }
  
  @DeleteMapping("/v1/jpa-sample/star/{id}")
  public ResponseEntity<?> removeStarById(
      @PathVariable Integer id) {
    //jpaSampleService.removeStarById(id);
    
    jpaSampleService.removeStarById(id);
    
    return ResponseEntity.noContent().build();
  }
  
}
