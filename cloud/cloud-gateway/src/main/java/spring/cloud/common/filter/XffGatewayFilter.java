package spring.cloud.common.filter;

import java.net.Inet4Address;
import java.net.InetAddress;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import spring.custom.common.constant.Constant;

@Component
@Slf4j
public class XffGatewayFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    /* for debug */ if (log.isInfoEnabled()) {
      ServerHttpRequest request = exchange.getRequest();
      String uri = request.getURI().toString();
      log.info(uri);
    }
    
    String clientIp = exchange.getRequest()
        .getHeaders()
        .getFirst(Constant.HTTP_HEADER.X_FORWARDED_FOR);
    
    if (clientIp == null || clientIp.isEmpty()) {
      InetAddress address = exchange.getRequest().getRemoteAddress().getAddress();
      if (address.isLoopbackAddress()) {
        clientIp = Constant.LOOPBACK_IP;
      } else if (address instanceof Inet4Address) {
        clientIp = address.getHostAddress();
      } else {
        clientIp = address.getHostAddress().split("%")[0];
      }
      
      ServerWebExchange mutatedExchange = exchange.mutate()
          .request(exchange.getRequest().mutate()
              .header(Constant.HTTP_HEADER.X_FORWARDED_FOR, clientIp)
              .build())
          .build();
      
      log.info("add HttpHeader '{}'", Constant.HTTP_HEADER.X_FORWARDED_FOR);
      return chain.filter(mutatedExchange);
    }
    
    return chain.filter(exchange);
  }
  
  @Override
  public int getOrder() {
    return -1;
  }
}
