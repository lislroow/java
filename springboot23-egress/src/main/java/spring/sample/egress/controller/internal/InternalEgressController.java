package spring.sample.egress.controller.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mgkim.webservice.soap.sayhello.types.GetNameRequest;
import net.mgkim.webservice.soap.sayhello.types.GetSayHelloResponse;
import spring.sample.code.SOAP_CLIENT_TYPE;
import spring.sample.config.validator.EnumValidator;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class InternalEgressController {
  
  private WebServiceTemplate webServiceTemplate;
  
  @Value("${egress.webservice.url}")
  private String egressWebserviceUrl;
  
  @GetMapping("/v1/internal/egress/soap/{clientType}")
  public Map<String, Object> soap(
      @PathVariable @EnumValidator(enumClazz = SOAP_CLIENT_TYPE.class) String clientType) {
    System.out.println(clientType);
    GetNameRequest request = new GetNameRequest();
    request.setName("myeonggu.kim");
    String url = String.format("%s%s", egressWebserviceUrl, "/soap/SayHello/types");
    GetSayHelloResponse res = (GetSayHelloResponse) webServiceTemplate.marshalSendAndReceive(url, request);
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("korean", res.getKorean());
    result.put("english", res.getEnglish());
    log.info("result={}", result);
    return result;
  }
  
  @GetMapping("/api/v1/external/hello-httpclient")
  public String wsHelloHttpclient() {
    String result = null;
    
    // 
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      HttpPost post = new HttpPost("http://172.28.200.1:9091/webservice/hello");
      post.setHeader("Content-Type", "text/xml");
      post.setHeader("Connection", "close");
      
      String xmlstr = "";
      xmlstr += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://ws.mgkim.net/hello\">";
      xmlstr += "<soapenv:Header/>";
      xmlstr += "<soapenv:Body>";
      xmlstr += "    <hel:getHelloRequest>";
      xmlstr += "        <hel:name>SMITH</hel:name>";
      xmlstr += "    </hel:getHelloRequest>";
      xmlstr += "</soapenv:Body>";
      xmlstr += "</soapenv:Envelope>";
      
      //xmlstr = "";
      //xmlstr += "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">";
      //xmlstr += "<Header />";
      //xmlstr += "<Body xmlns=\"http://ws.mgkim.net/hello/\">";
      //xmlstr += "  <getHelloRequest>";
      //xmlstr += "    <name>Smith</name>";
      //xmlstr += "  </getHelloRequest>";
      //xmlstr += "</Body>";
      //xmlstr += "</Envelope>";
      log.info("xmlstr={}", xmlstr);
      try {
        StringEntity entity = new StringEntity(xmlstr);
        post.setEntity(entity);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      try (CloseableHttpResponse response = client.execute(post)) {
        HttpEntity responseEntity = response.getEntity();
        String resstr = EntityUtils.toString(responseEntity);
        log.info("resstr:{}", resstr);
        result = resstr;
      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // --
    
    
    // parsing
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    
    /*
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8)));
      Element root = document.getDocumentElement();
      log.info("root={}", root);
      NodeList nodeList = document.getElementsByTagNameNS("http://ws.mgkim.net/hello", "getHelloResponse");
      for (int i=0; i<nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String message = element.getElementsByTagNameNS("http://ws.mgkim.net/hello", "message")
              .item(0).getTextContent();
          log.info("message={}", message);
        }
      }
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    */
    return result;
  }
  
  @GetMapping("/api/v1/external/hello-via-zuul")
  public String wsHelloViaZuul() {
    String result = null;
    
    // 
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      HttpPost post = new HttpPost("http://localhost:8080/gov/ws");
      post.setHeader("Content-Type", "text/xml");
      post.setHeader("Connection", "close");
      
      String xmlstr = "";
      xmlstr += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://ws.mgkim.net/hello\">";
      xmlstr += "<soapenv:Header/>";
      xmlstr += "<soapenv:Body>";
      xmlstr += "    <hel:getHelloRequest>";
      xmlstr += "        <hel:name>SMITH</hel:name>";
      xmlstr += "    </hel:getHelloRequest>";
      xmlstr += "</soapenv:Body>";
      xmlstr += "</soapenv:Envelope>";
      
      //xmlstr = "";
      //xmlstr += "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">";
      //xmlstr += "<Header />";
      //xmlstr += "<Body xmlns=\"http://ws.mgkim.net/hello/\">";
      //xmlstr += "  <getHelloRequest>";
      //xmlstr += "    <name>Smith</name>";
      //xmlstr += "  </getHelloRequest>";
      //xmlstr += "</Body>";
      //xmlstr += "</Envelope>";
      log.info("xmlstr={}", xmlstr);
      try {
        StringEntity entity = new StringEntity(xmlstr);
        post.setEntity(entity);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      
      try (CloseableHttpResponse response = client.execute(post)) {
        HttpEntity responseEntity = response.getEntity();
        String resstr = EntityUtils.toString(responseEntity);
        log.info("resstr:{}", resstr);
        result = resstr;
      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // --
    
    
    // parsing
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    
    /*
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8)));
      Element root = document.getDocumentElement();
      log.info("root={}", root);
      NodeList nodeList = document.getElementsByTagNameNS("http://ws.mgkim.net/hello", "getHelloResponse");
      for (int i=0; i<nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String message = element.getElementsByTagNameNS("http://ws.mgkim.net/hello", "message")
              .item(0).getTextContent();
          log.info("message={}", message);
        }
      }
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
     */
    return result;
  }
  
  
  @GetMapping("/api/v1/external/hello-via-zuul-proxy")
  public String wsHelloViaZuulProxy() {
    String result = null;
    HttpHost proxyHost = new HttpHost("localhost", 8080, "http");
//    HttpHost proxyHost = new HttpHost("forward.mgkim.net", 8888, "http");
    try (CloseableHttpClient client = HttpClients.custom()
        .setProxy(proxyHost)
        .setRedirectStrategy(new LaxRedirectStrategy())
        .build()) {
        //HttpPost post = new HttpPost("http://forward.mgkim.net:8888/ws");
        HttpPost post = new HttpPost("http://localhost:8083/ws");
        post.setHeader("Content-Type", "text/xml");
        post.setHeader("Connection", "close");
        
        String xmlstr = "";
        xmlstr += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://ws.mgkim.net/hello\">";
        xmlstr += "<soapenv:Header/>";
        xmlstr += "<soapenv:Body>";
        xmlstr += "    <hel:getHelloRequest>";
        xmlstr += "        <hel:name>Mr. LEE</hel:name>";
        xmlstr += "    </hel:getHelloRequest>";
        xmlstr += "</soapenv:Body>";
        xmlstr += "</soapenv:Envelope>";
        
        //xmlstr = "";
        //xmlstr += "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        //xmlstr += "<Header />";
        //xmlstr += "<Body xmlns=\"http://ws.mgkim.net/hello/\">";
        //xmlstr += "  <getHelloRequest>";
        //xmlstr += "    <name>Smith</name>";
        //xmlstr += "  </getHelloRequest>";
        //xmlstr += "</Body>";
        //xmlstr += "</Envelope>";
        log.info("xmlstr={}", xmlstr);
        try {
          StringEntity entity = new StringEntity(xmlstr);
          post.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        
        try (CloseableHttpResponse response = client.execute(post)) {
          HttpEntity responseEntity = response.getEntity();
          String resstr = EntityUtils.toString(responseEntity);
          log.info("resstr:{}", resstr);
          result = resstr;
        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    
    return result;
  }
  
}
