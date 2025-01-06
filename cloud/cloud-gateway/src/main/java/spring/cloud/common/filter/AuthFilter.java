package spring.cloud.common.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import spring.cloud.common.client.ApiClient;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.exception.AppException;
import spring.custom.dto.TokenReqDto;
import spring.custom.dto.TokenResDto;

@Profile({"local", "dev"})
@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  @Value("${cloud.gateway.filter.auth-url:http://localhost/auth-api/v1/token/verify}")
  private String AUTH_URL;
  
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
        
        ERROR_CODE failCode = ERROR_CODE.A002;
        TokenReqDto.Verify requestBody = new TokenReqDto.Verify();
        requestBody.setAtkUuid(token);
        try {
          final TokenResDto.Verify vertifyDto = 
              apiClient.postForEntity(AUTH_URL, requestBody, TokenResDto.Verify.class);
          exchange = exchange.mutate()
              .request(req -> 
                req.header(HttpHeaders.AUTHORIZATION, "Bearer " + vertifyDto.getAccessToken()))
              .build();
        } catch (Exception e) {
          /* for debug */ if (log.isDebugEnabled()) e.printStackTrace();
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
