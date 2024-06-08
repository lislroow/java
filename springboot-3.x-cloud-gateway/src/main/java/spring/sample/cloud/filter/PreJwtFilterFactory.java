package spring.sample.cloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class PreJwtFilterFactory extends AbstractGatewayFilterFactory<PreJwtFilterFactory.Config> {
  
  public PreJwtFilterFactory() {
    super(Config.class);
    System.out.println("created");
  }
  
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      String authorizationHeader = exchange.getRequest().getHeaders().getFirst(config.headerName);
      if (StringUtils.hasText(authorizationHeader) &&
          authorizationHeader.startsWith(config.granted)) {
        int substrIdx = config.getGranted().length() > 0 ? config.getGranted().length() + 1 : 0;
        String token = authorizationHeader.substring(substrIdx);
        if (token.length() > 0) { // is valid?
          return chain.filter(exchange);
        }
      }
      return unauthorizedResponse(exchange);
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
