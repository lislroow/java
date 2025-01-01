package spring.cloud.common.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.exception.AppException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {
  
  final ObjectMapper objectMapper;
  
  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    /* for debug */ if (log.isDebugEnabled()) log.info("");
    return response.getStatusCode().isError();
  }
  
  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    /* for debug */ if (log.isDebugEnabled()) log.info("");
    if (response.getStatusCode().is4xxClientError()) {
      throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
    } else if (response.getStatusCode().is5xxServerError()) {
      try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()))) {
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
          builder.append(line);
        }
        ResponseDto<?> resDto = objectMapper.readValue(builder.toString(), ResponseDto.class);
        String code = resDto.getHeader().getCode();
        String message = resDto.getHeader().getMessage();
        throw new AppException(code, message);
      } catch (Exception e) {
        throw e;
      }
    }
  }
  
}
