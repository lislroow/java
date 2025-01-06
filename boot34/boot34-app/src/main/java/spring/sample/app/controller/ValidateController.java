package spring.sample.app.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dto.ValidateReqDto;
import spring.sample.app.dto.ValidateResDto;
import spring.sample.app.service.ValidateService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ValidateController {
  
  final ModelMapper modelMapper;

  @GetMapping("/v1/validate/person/model-attribute")
  public ResponseEntity<ValidateResDto.PersonInfo> modelAttribute(
      @Valid @ModelAttribute ValidateReqDto.PersonModel param) {
    
    ValidateResDto.PersonInfo resDto = modelMapper.map(param, ValidateResDto.PersonInfo.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/validate/person/request-body")
  public ResponseEntity<ValidateResDto.PersonInfo> requestBody(
      @Valid @RequestBody ValidateReqDto.RegistPerson param) {
    
    ValidateResDto.PersonInfo resDto = modelMapper.map(param, ValidateResDto.PersonInfo.class);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/validate/person/request-param")
  public Integer requestParam(
      @RequestParam @Min(value = 0) Integer number) {
    Integer result = null;
    try {
      result = 10 / number;
    } catch (ArithmeticException e) {
      throw e;
    }
    return result;
  }
  
  @GetMapping("/v1/validate/person/path-variable/{number}")
  public Integer pathVariable(
      @PathVariable @Min(value = 0) Integer number) {
    Integer result = null;
    try {
      result = 10 / number;
    } catch (ArithmeticException e) {
      throw e;
    }
    return result;
  }
  
  @Autowired
  private ValidateService personService;
  
  @PostMapping("/v1/validate/person/service-aop")
  public ResponseEntity<ValidateResDto.PersonInfo> serviceAop(@RequestBody ValidateReqDto.RegistPerson param) {
    log.info("param: {}", param);
    ValidateReqDto.RegistPerson result = personService.aop(param);
    ValidateResDto.PersonInfo resDto = modelMapper.map(result, ValidateResDto.PersonInfo.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/api/validate/service-jakarta")
  public ResponseEntity<ValidateResDto.PersonInfo> serviceJakarta(@RequestBody ValidateReqDto.RegistPerson param) {
    log.info("param: {}", param);
    ValidateReqDto.RegistPerson result = personService.jakarta(param);
    ValidateResDto.PersonInfo resDto = modelMapper.map(result, ValidateResDto.PersonInfo.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/api/validate/service-spring")
  public ResponseEntity<ValidateResDto.PersonInfo> serviceSpring(
      @RequestBody ValidateReqDto.RegistPerson param) throws Exception {
    
    log.info("param: {}", param);
    ValidateReqDto.RegistPerson result = personService.spring(param);
    ValidateResDto.PersonInfo resDto = modelMapper.map(result, ValidateResDto.PersonInfo.class);
    return ResponseEntity.ok(resDto);
  }
}
