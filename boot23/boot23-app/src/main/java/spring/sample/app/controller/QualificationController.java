package spring.sample.app.controller;

import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.RESPONSE_CODE;
import spring.sample.app.dto.QualificationResDto;
import spring.sample.app.feign.FeignQualificationController;

@RestController
@Validated
@AllArgsConstructor
public class QualificationController {
  
  final ModelMapper modelMapper;
  final FeignQualificationController qualificationFeignClient;
  
  @PostMapping("/v1/qualification/verify-using-webservice")
  public ResponseDto<QualificationResDto.SoapRes> verifyUsingWebservice() {
    ResponseDto<QualificationResDto.SoapRes> result = 
        qualificationFeignClient.verifyUsingWebservice();
    
    QualificationResDto.SoapRes body = null;
    if (RESPONSE_CODE.S000.name().equals(result.getHeader().getCode())) {
      body = result.getBody();
    }
    return ResponseDto.body(body);
  }
  
}
