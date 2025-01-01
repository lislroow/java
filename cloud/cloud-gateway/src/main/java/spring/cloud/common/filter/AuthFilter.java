package spring.cloud.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import spring.cloud.api.dto.TokenResDto;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  
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
      log.info(token);
      
      ResponseEntity<TokenResDto.Verify> resDto = new RestTemplate().getForEntity("http://localhost/auth", TokenResDto.Verify.class);
      log.info("{}", resDto);
      return chain.filter(exchange);
    };
  }
  
  @Data
  public static class Config {
  }
}
