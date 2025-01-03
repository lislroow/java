package spring.cloud.common.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import spring.cloud.common.client.ApiClient;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.RESPONSE;
import spring.custom.common.exception.AppException;
import spring.custom.dto.TokenReqDto;
import spring.custom.dto.TokenResDto;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  //@Value("${cloud.gateway.auth-url}")
  private final String AUTH_URL = "http://localhost/auth-api/v1/token/verify";
  
  @Autowired
  private ApiClient apiClient;
  
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
        
        /* for debug */ if (log.isDebugEnabled()) log.info(token);
        
        ResponseDto<TokenResDto.Verify> resDto = null;
        
        RESPONSE failCode = RESPONSE.A002;
        TokenReqDto.Verify requestBody = new TokenReqDto.Verify();
        requestBody.setAtkUuid(token);
        try {
          resDto = apiClient.post(AUTH_URL, requestBody, TokenResDto.Verify.class);
          RESPONSE code = RESPONSE.fromCode(resDto.getHeader().getCode());
          if (code != RESPONSE.S000) {
            log.error("{} > {}", code, failCode);
            throw new AppException(failCode);
          }
          /* for debug */ if (log.isDebugEnabled()) log.info("{}", resDto.getBody());
        } catch (AppException e) {
          log.error("{} > {}", e, failCode);
          throw new AppException(failCode);
          
        } catch (HttpServerErrorException e) {
          log.error("{} > {}", e.getMessage(), failCode);
          throw new AppException(failCode);
          
        } catch (HttpClientErrorException e) {
          log.error("{} > {}", e.getMessage(), failCode);
          throw new AppException(failCode);
          
        } catch (Exception e) {
          log.error("{} > {}", e.getMessage(), failCode);
          throw new AppException(failCode);
        }
      }
      return chain.filter(exchange);
    };
  }
  
  @Data
  public static class Config {
  }
}
