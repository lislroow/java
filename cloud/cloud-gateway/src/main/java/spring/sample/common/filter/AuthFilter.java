package spring.sample.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
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
    return new OrderedGatewayFilter((exchange, chain) -> {
      /* for debug */ log.debug("");
        return chain.filter(exchange);
      }, Ordered.LOWEST_PRECEDENCE);
  }
  
  @Data
  public static class Config {
  }
}
