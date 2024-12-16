package spring.sample.app.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dto.PersonReqDto;
import spring.sample.app.dto.PersonResDto;
import spring.sample.app.service.PersonService;
import spring.sample.common.dto.ResponseDto;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PersonController {
  
  final ModelMapper modelMapper;

  @GetMapping("/v1/person/model-attribute")
  public ResponseDto<PersonResDto.PersonInfo> modelAttribute(
      @Valid @ModelAttribute PersonReqDto.PersonModel param) {
    
    PersonResDto.PersonInfo resDto = modelMapper.map(param, PersonResDto.PersonInfo.class);
    return ResponseDto.body(resDto);
  }
  
  @PostMapping("/v1/person/request-body")
  public ResponseDto<PersonResDto.PersonInfo> requestBody(
      @Valid @RequestBody PersonReqDto.RegistPerson param) {
    
    PersonResDto.PersonInfo resDto = modelMapper.map(param, PersonResDto.PersonInfo.class);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/person/request-param")
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
  
  @GetMapping("/v1/person/path-variable/{number}")
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
  private PersonService personService;
  
  @PostMapping("/v1/person/service/aop")
  public ResponseDto<PersonResDto.PersonInfo> serviceAop(@RequestBody PersonReqDto.RegistPerson param) {
    log.info("param: {}", param);
    PersonReqDto.RegistPerson result = personService.aop(param);
    PersonResDto.PersonInfo resDto = modelMapper.map(result, PersonResDto.PersonInfo.class);
    return ResponseDto.body(resDto);
  }
  
  @PostMapping("/api/validate/service/jakarta")
  public ResponseDto<PersonResDto.PersonInfo> serviceJakarta(@RequestBody PersonReqDto.RegistPerson param) {
    log.info("param: {}", param);
    PersonReqDto.RegistPerson result = personService.jakarta(param);
    PersonResDto.PersonInfo resDto = modelMapper.map(result, PersonResDto.PersonInfo.class);
    return ResponseDto.body(resDto);
  }
  
  @PostMapping("/api/validate/service/spring")
  public ResponseDto<PersonResDto.PersonInfo> serviceSpring(
      @RequestBody PersonReqDto.RegistPerson param) throws Exception {
    
    log.info("param: {}", param);
    PersonReqDto.RegistPerson result = personService.spring(param);
    PersonResDto.PersonInfo resDto = modelMapper.map(result, PersonResDto.PersonInfo.class);
    return ResponseDto.body(resDto);
  }
}
