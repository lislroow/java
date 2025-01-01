package spring.cloud.common.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.dto.ResponseDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiClient {
  
  final RestTemplate restTemplate;
  final ObjectMapper objectMapper;
  
  public <T> ResponseDto<T> get(String url, Class<T> responseType) {
    ResponseDto<T> resDto = null;
    ResponseEntity<String> resEntity = restTemplate.getForEntity(url, String.class);
    String body = resEntity.getBody();
    JavaType javaType = objectMapper.getTypeFactory()
        .constructParametricType(ResponseDto.class, responseType);
    try {
      resDto = objectMapper.readValue(body, javaType);
    } catch (JsonProcessingException e) {
      log.error("{}", e.getMessage());
    }
    return resDto;
  }
  
}
