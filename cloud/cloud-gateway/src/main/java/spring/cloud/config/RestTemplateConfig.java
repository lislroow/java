package spring.cloud.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
//import spring.cloud.common.exception.RestTemplateErrorHandler;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {
  
  // new HttpComponentsClientHttpRequestFactory(httpClient); boot 2.x compatibility issue
  
  @Bean
  RestTemplate restTemplate() {
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectionRequestTimeout(Timeout.ofSeconds(1))
        .setResponseTimeout(Timeout.ofSeconds(5))
        .build();
    
    CloseableHttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .build();
    
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    RestTemplate restTemplate = new RestTemplate(factory);
    //restTemplate.setErrorHandler(restTemplateErrorHandler);
    return restTemplate;
  }
  
}
