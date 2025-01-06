package spring.cloud.common.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.exception.AppException;

//@Component
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
        ResponseEntity<ProblemDetail> resDto = objectMapper.readValue(builder.toString(), ResponseEntity.class);
        String title = resDto.getBody().getTitle();
        ERROR_CODE errorCode = ERROR_CODE.fromCode(title)
            .orElseThrow(() -> new IllegalArgumentException(String.format("'%s' not exist.", title)));
        throw new AppException(errorCode);
      } catch (Exception e) {
        throw e;
      }
    }
  }
  
}
