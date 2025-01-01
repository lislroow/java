package spring.cloud.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import spring.cloud.api.dto.TokenResDto;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.RESPONSE;
import spring.custom.common.exception.AppException;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  //@Value("${cloud.gateway.auth-url}")
  private String authUrl = "http://localhost/auth-api";
  
  public AuthFilter() {
    super(Config.class);
  }
  
  @Override
  public GatewayFilter apply(AuthFilter.Config config) {
    return (exchange, chain) -> {
      /* for debug */ if (log.isDebugEnabled()) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        log.debug(uri);
      }
      ServerHttpRequest request = exchange.getRequest();
      String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
      if (token != null && (token.startsWith("Bearer") || token.startsWith("bearer"))) {
        token = token.substring(7);
      }
      log.info(token);
      
      String api = String.format("%s/v1/token/verify/%s", authUrl, token);
      ResponseDto<TokenResDto.Verify> resDto = null;
      
      RESPONSE failCode = RESPONSE.A002;
      try {
        resDto = new RestTemplate().exchange(
            api, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseDto<TokenResDto.Verify>>() {}).getBody();
        RESPONSE code = RESPONSE.fromCode(resDto.getHeader().getCode());
        if (code != RESPONSE.S000) {
          log.error("{} > {}", code, failCode);
          throw new AppException(failCode);
        }
        
        log.info("{}", resDto.getBody());
      } catch (HttpServerErrorException e) {
        HttpServerErrorException se = (HttpServerErrorException) e;
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType javaType = typeFactory.constructParametricType(
            ResponseDto.class, TokenResDto.Verify.class
        );
        /* for debug */ if (log.isDebugEnabled()) System.err.println(se.getResponseBodyAsString());
        try {
          resDto = objectMapper.readValue(se.getResponseBodyAsString(), javaType);
        } catch (JsonProcessingException e1) {
          log.error("{} > {}", e1.getMessage(), failCode);
          throw new AppException(failCode);
        }
        RESPONSE code = RESPONSE.fromCode(resDto.getHeader().getCode());
        log.error("{} > {}", code, failCode);
        throw new AppException(failCode);
      } catch (AppException e) {
        throw e;
      } catch (Exception e) {
        log.error("{} > {}", e.getMessage(), failCode);
        throw new AppException(failCode);
      }
      return chain.filter(exchange);
    };
  }
  
  @Data
  public static class Config {
  }
}
