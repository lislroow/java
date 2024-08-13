package spring.sample.webservice.endpoint;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mgkim.ws.member.GetHelloRequest;
import net.mgkim.ws.member.GetHelloResponse;

@Endpoint
@Slf4j
public class MemberEndpoint {
  
  private static final String MEMBER_NS_URI = "http://ws.mgkim.net/member";
  
  @PayloadRoot(namespace = MEMBER_NS_URI, localPart = "getHelloRequest")
  @ResponsePayload
  public GetHelloResponse getMember(@RequestPayload GetHelloRequest request,
      MessageContext messageContext,
      @SoapHeader(value = "serviceName") SoapHeaderElement serviceName,
      @SoapHeader(value = "useSystemCode") SoapHeaderElement useSystemCode) {
    SaajSoapMessage soapResponse = (SaajSoapMessage) messageContext.getResponse();
    //org.springframework.ws.soap.SoapHeader header =
    org.springframework.ws.soap.SoapHeader soapResponseHeader = soapResponse.getSoapHeader();
    //soapResponseHeader.
    
    CommonHeader commonHeader = CommonHeader.builder()
      .serviceName("testService")
      .useSystemCode("testSystemCode")
      .build();
    
    GetHelloResponse response = new GetHelloResponse();
    String message = "Hello, " + request.getName() + "!";
    log.info("serviceName:{}, useSystemCode:{}, message:{}",
        serviceName.getText(), useSystemCode.getText(), message);
    response.setMessage(message);
    
    JAXBContext jaxbContext;
    try {
      jaxbContext = JAXBContext.newInstance(CommonHeader.class);
      jaxbContext.createMarshaller().marshal(commonHeader, soapResponseHeader.getResult());
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    
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
