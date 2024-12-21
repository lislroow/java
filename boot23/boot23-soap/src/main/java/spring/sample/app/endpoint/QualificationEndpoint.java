package spring.sample.app.endpoint;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.server.endpoint.annotation.SoapHeader;

import localhost._15200.boot23_soap.qualification.types.GetQualificationRequest;
import localhost._15200.boot23_soap.qualification.types.GetQualificationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.config.WebMvcConfig;

@Endpoint
@Slf4j
public class QualificationEndpoint {
  
  @PayloadRoot(namespace = WebMvcConfig.NS_QUALIFICATION, localPart = "GetQualificationRequest")
  @ResponsePayload
  public GetQualificationResponse getGetQualificationRequest(@RequestPayload GetQualificationRequest request,
      MessageContext messageContext,
      @SoapHeader(value = "serviceName") SoapHeaderElement serviceName,
      @SoapHeader(value = "useSystemCode") SoapHeaderElement useSystemCode) {
    /*
    SaajSoapMessage soapResponse = (SaajSoapMessage) messageContext.getResponse();
    org.springframework.ws.soap.SoapHeader soapResponseHeader = soapResponse.getSoapHeader();
    CommonHeader commonHeader = CommonHeader.builder()
      .serviceName("testService")
      .useSystemCode("testSystemCode")
      .build();
    */
    GetQualificationResponse response = new GetQualificationResponse();
    response.setId("id");
    response.setQualified(true);
    /*
    JAXBContext jaxbContext;
    try {
      jaxbContext = JAXBContext.newInstance(CommonHeader.class);
      jaxbContext.createMarshaller().marshal(commonHeader, soapResponseHeader.getResult());
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    */
    return response;
  }
  
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CommonHeader {
    private String serviceName;
    private String useSystemCode;
  }
}
