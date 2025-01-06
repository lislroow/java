package spring.cloud.common.client;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.exception.AppException;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
@Slf4j
public class ApiClient {
  
  final RestTemplate restTemplate;
  final ObjectMapper objectMapper;
  
  public <T> T getForEntity(String url, Class<T> responseType) {
    ResponseEntity<String> resEntity = restTemplate.getForEntity(url, String.class);
    String body = resEntity.getBody();
    T result = null;
    try {
      result = objectMapper.readValue(body, responseType);
    } catch (JsonProcessingException e) {
      log.error("{}", e.getMessage());
    }
    return result;
  }
  
  public <T> T postForEntity(String url, Object requestBody, Class<T> responseType) {
    ResponseEntity<String> resEntity = null;
    try {
      resEntity = restTemplate.postForEntity(url, requestBody, String.class);
    } catch (HttpServerErrorException | HttpClientErrorException e) {
      /* for debug */ if (log.isDebugEnabled()) System.out.println("HTTP Status Code: " + e.getStatusCode());
      /* for debug */ if (log.isDebugEnabled()) System.out.println("Response Body: " + e.getResponseBodyAsString());
      try {
        ProblemDetail problemDetail = new ObjectMapper().readValue(
            e.getResponseBodyAsString(), 
            ProblemDetail.class
        );
        Optional<ERROR_CODE> errorCode = ERROR_CODE.fromCode(problemDetail.getTitle());
        if (errorCode.isPresent()) {
          throw new AppException(errorCode.get());
        } else {
          throw e;
        }
      } catch (JsonProcessingException jsonException) {
        /* for debug */ if (log.isDebugEnabled()) System.err.println("Failed to parse response body to ProblemDetail: " + jsonException.getMessage());
        throw new AppException(ERROR_CODE.E999.code(), jsonException.getMessage());
      }
    } catch (RestClientException e) {
      /* for debug */ if (log.isDebugEnabled()) e.printStackTrace();
      throw new AppException(ERROR_CODE.E999.code(), e.getMessage());
    }
    
    String body = resEntity.getBody();
    T result = null;
    try {
      result = objectMapper.readValue(body, responseType);
    } catch (JsonProcessingException e) {
      /* for debug */ if (log.isDebugEnabled()) System.err.println("Failed to parse response body to resDto: " + e.getMessage());
      throw new AppException(ERROR_CODE.E999.code(), e.getMessage());
    }
    return result;
  }
  
}
