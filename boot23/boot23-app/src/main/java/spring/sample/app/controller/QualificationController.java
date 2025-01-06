package spring.sample.app.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import spring.sample.app.dto.QualificationResDto;
import spring.sample.app.feign.FeignQualificationController;

@RestController
@Validated
@AllArgsConstructor
public class QualificationController {
  
  final ModelMapper modelMapper;
  final FeignQualificationController qualificationFeignClient;
  
  @PostMapping("/v1/qualification/verify-using-webservice")
  public ResponseEntity<QualificationResDto.SoapRes> verifyUsingWebservice() {
    ResponseEntity<QualificationResDto.SoapRes> result = 
        qualificationFeignClient.verifyUsingWebservice();
    QualificationResDto.SoapRes body = result.getBody();
    return ResponseEntity.ok(body);
  }
  
}
