package spring.sample.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import spring.sample.config.properties.SecurityConfigProperties;

public class AuthFilterFactory extends AbstractGatewayFilterFactory<AuthFilterFactory.Config> {

  private final String SECRET_KEY;
  
  public AuthFilterFactory(SecurityConfigProperties properties) {
    super(Config.class);
    SECRET_KEY = properties.getTokenSignkey();
  }

  /*
  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
  */
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      return chain.filter(exchange);
      /*
      String authorizationHeader = exchange.getRequest().getHeaders().getFirst(config.headerName);
      if (StringUtils.hasText(authorizationHeader) &&
          authorizationHeader.startsWith(config.granted)) {
        int substrIdx = config.getGranted().length() > 0 ? config.getGranted().length() + 1 : 0;
        String token = authorizationHeader.substring(substrIdx);
        Jws<Claims> jws = null;
        try {
          jws = isValidToken(token); // is valid?
        } catch (Exception e) {
          System.err.println(e);
          return unauthorizedResponse(exchange);
        }
        if (jws != null) {
          String xtoken = token.split("\\.")[1];
          exchange.getRequest().mutate().header("x-token", xtoken);
          exchange.getRequest().mutate().header("x-user-id", jws.getPayload().get("id").toString());
          return chain.filter(exchange);
        }
      }
      return unauthorizedResponse(exchange);
      */
    };
  }

  /*
  private Jws<Claims> isValidToken(String token) {
    Jws<Claims> jws = null;
    try {
      jws = Jwts.parser()
          .verifyWith(getSigningKey())
          .build()
          .parseSignedClaims(token);
    } catch (Exception e) {
      throw e;
    }
    return jws;
  }
  */
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
