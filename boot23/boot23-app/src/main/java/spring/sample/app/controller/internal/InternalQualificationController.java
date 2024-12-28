package spring.sample.app.controller.internal;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

import https.soap_mgkim_net.v1.qualification.types.GetQualificationRequest;
import https.soap_mgkim_net.v1.qualification.types.GetQualificationResponse;
import lombok.AllArgsConstructor;
import spring.custom.common.dto.ResponseDto;
import spring.sample.app.dto.QualificationResDto;

@RestController
@Validated
@AllArgsConstructor
public class InternalQualificationController {

  private static final String SOAP_GATEWAY_NAME = "boot23-soap";
  
  final ModelMapper modelMapper;
  final WebServiceTemplate webServiceTemplate;
  
  @PostMapping("/v1/internal/qualification/verify-using-webservice")
  public ResponseDto<QualificationResDto.SoapRes> verifyUsingWebservice() {
    String url = "http://localhost:10100/"+SOAP_GATEWAY_NAME+"/v1/qualification/types";
    GetQualificationRequest request = new GetQualificationRequest();
    request.setId("1");
    GetQualificationResponse response = 
        (GetQualificationResponse) webServiceTemplate.marshalSendAndReceive(url, request);
    QualificationResDto.SoapRes res = modelMapper.map(response, QualificationResDto.SoapRes.class);
    return ResponseDto.body(res);
  }
  
  @PostMapping("/v1/internal/qualification/verify-using-httpclient")
  public ResponseDto<Map<String, Object>> verifyUsingHttpclient() {
    String url = "http://localhost:10100/"+SOAP_GATEWAY_NAME+"/v1/qualification/types";
    return ResponseDto.body();
  }
}
