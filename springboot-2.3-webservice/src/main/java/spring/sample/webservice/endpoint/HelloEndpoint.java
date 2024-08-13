package spring.sample.webservice.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.extern.slf4j.Slf4j;
import net.mgkim.ws.hello.GetHelloRequest;
import net.mgkim.ws.hello.GetHelloResponse;

@Endpoint
@Slf4j
public class HelloEndpoint {
  
  private static final String NAMESPACE_URI = "http://ws.mgkim.net/hello";
  
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHelloRequest")
  @ResponsePayload
  public GetHelloResponse getHello(@RequestPayload GetHelloRequest request) {
    GetHelloResponse response = new GetHelloResponse();
    String message = "Hello, " + request.getName() + "!";
    log.info("message:{}", message);
    response.setMessage(message);
    return response;
  }
}
