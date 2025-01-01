package spring.cloud.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (log.isInfoEnabled()) {
      ServerHttpRequest request = exchange.getRequest();
      String uri = request.getURI().toString();
      log.info(uri);
    }
    return chain.filter(exchange);
  }
  
  @Override
  public int getOrder() {
    return -1;
  }
}
