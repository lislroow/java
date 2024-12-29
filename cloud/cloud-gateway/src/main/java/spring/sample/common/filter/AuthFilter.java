package spring.sample.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import spring.sample.config.properties.SecurityConfigProperties;

@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

  private final String SECRET_KEY;
  
  public AuthFilter(SecurityConfigProperties properties) {
    super(Config.class);
    SECRET_KEY = properties.getTokenSignkey();
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      log.info("hello");
      return chain.filter(exchange);
    };
  }

  private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
  }
  
  public static class Config {
    private String headerName; // Authorization
    private String granted; // Bearer
    public String getHeaderName() {
      return headerName;
    }
    public void setHeaderName(String headerName) {
      this.headerName = headerName;
    }
    public String getGranted() {
      return granted;
    }
    public void setGranted(String granted) {
      this.granted = granted;
    }
  }
}
