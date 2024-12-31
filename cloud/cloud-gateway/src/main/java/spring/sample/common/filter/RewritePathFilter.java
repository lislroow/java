package spring.sample.common.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RewritePathFilter extends AbstractGatewayFilterFactory<Object> {

  @Override
  public GatewayFilter apply(Object config) {
    return new OrderedGatewayFilter((exchange, chain) -> {
      String routeId = (String) exchange.getAttributes()
          .get(ServerWebExchangeUtils.GATEWAY_PREDICATE_MATCHED_PATH_ROUTE_ID_ATTR);
      if (routeId != null) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/" + routeId)) {
          String newPath = path.replaceFirst("/" + routeId, "");
          ServerHttpRequest request = exchange.getRequest();
          ServerHttpRequest newRequest = request.mutate().path(newPath).build();
          exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
          /* for debug */ if (log.isDebugEnabled()) {
            String uri = newRequest.getURI().toString();
            log.debug(uri);
          }
          return chain.filter(exchange.mutate().request(newRequest).build());
        }
      }
      return chain.filter(exchange);
    }, Ordered.HIGHEST_PRECEDENCE);
  }
}
