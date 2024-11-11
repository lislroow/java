package spring.sample.app.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.extern.slf4j.Slf4j;
import net.mgkim.webservice.soap.sayhello.types.GetNameRequest;
import net.mgkim.webservice.soap.sayhello.types.GetSayHelloResponse;
import spring.sample.config.WebMvcConfig;

@Endpoint
@Slf4j
public class SayHelloEndpoint {
  
  @PayloadRoot(namespace = WebMvcConfig.NS_SAY_HELLO, localPart = "getNameRequest")
  @ResponsePayload
  public GetSayHelloResponse getSayHello(@RequestPayload GetNameRequest request) {
    GetSayHelloResponse response = new GetSayHelloResponse();
    response.setKorean(String.format("안녕, %s", request.getName()));
    response.setEnglish(String.format("Hello, %s", request.getName()));
    return response;
  }
}
