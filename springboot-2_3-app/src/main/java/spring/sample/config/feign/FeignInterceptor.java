package spring.sample.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FeignInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Buffer requestBuffer = new Buffer();
    if(request.body() != null) {
      request.body().writeTo(requestBuffer);
    }

    log.info("[Feign]================================ Start Feign ====================================");
    log.info("[Feign] Request    : [{}]", request);
    for (String name : request.headers().names()) {
      log.info("[Feign] RequestHeader  : [{}:{}]", name, request.header(name));
    }
    log.info("[Feign] RequestBody  : [{}]", requestBuffer.readUtf8());

    Response response = chain.proceed(request);

    log.info("[Feign] Response    : [{}]", response);

    HttpStatus resHttpStatus = HttpStatus.valueOf(response.code());

    for (String name : response.headers().names()) {
      log.info("[Feign] ResponseHeader  : [{}:{}]", name, response.header(name));
    }

    String contentType = response.header("Content-Type");

    if(StringUtils.isBlank(contentType)) {
      contentType = MediaType.APPLICATION_JSON_VALUE;
    }

    // Respons Body logging
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> resMap = new HashMap<>();
    ResponseBody responseBody = response.body();
    if (responseBody != null && responseBody.contentLength() != 0 && StringUtils.startsWith(contentType, MediaType.APPLICATION_JSON_VALUE)) {
      resMap = objectMapper.readValue(responseBody.string(), Map.class);

      Map<String, Object> head = (Map<String, Object>) resMap.get("head");
      if (head != null) {
        String resultCode = String.valueOf(head.get("resultCode"));
        if (!StringUtils.equals(resultCode, "0000")) {
          String resultMsg = String.valueOf(head.get("resultMsg"));

          resMap.put("errorCode", resultCode);
          resMap.put("errorMessage", resultMsg);

          resHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
      }
    }

    log.info("[Feign] ResponseData  : [{}]", resMap);

    ResponseBody body = null;
    if(StringUtils.startsWith(contentType, MediaType.APPLICATION_JSON_VALUE)) {
      String result = objectMapper.writeValueAsString(resMap);
      body = ResponseBody.create(okhttp3.MediaType.parse(contentType), result);
    } else if(StringUtils.startsWith(contentType, MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
      byte[] bytes = new byte[1024];
      if(response.body() != null) {
        bytes = response.body().bytes();
      }
      body = ResponseBody.create(okhttp3.MediaType.parse(contentType), bytes);
    }

    response = response.newBuilder().code(resHttpStatus.value()).body(body).build();

    log.info("[Feign]================================End Feign Response======================================");

    return response;
  }
}