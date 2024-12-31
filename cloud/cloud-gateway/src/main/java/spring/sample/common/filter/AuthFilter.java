package spring.sample.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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
      return chain.filter(exchange);
    };
  }
  
  @Data
  public static class Config {
  }
}
