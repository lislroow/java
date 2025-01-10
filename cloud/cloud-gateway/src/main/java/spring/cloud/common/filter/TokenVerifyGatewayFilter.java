package spring.cloud.common.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.client.ApiClient;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.Error;
import spring.custom.common.exception.AccessTokenExpiredException;
import spring.custom.common.exception.AppException;
import spring.custom.common.util.IdGenerator;
import spring.custom.common.util.XffClientIpExtractor;
import spring.custom.dto.TokenReqDto;
import spring.custom.dto.TokenResDto;

@Profile({"local", "dev"})
@Component
@Slf4j
public class TokenVerifyGatewayFilter extends AbstractGatewayFilterFactory<TokenVerifyGatewayFilter.Config> {
  
  @Value("${cloud.gateway.filter.auth-url:http://localhost/auth-api/v1/token/verify}")
  private String AUTH_URL;
  
  @Autowired
  private ApiClient apiClient;
  
  public TokenVerifyGatewayFilter() {
    super(Config.class);
  }
  
  @Override
  public GatewayFilter apply(TokenVerifyGatewayFilter.Config config) {
    return (exchange, chain) -> {
      /* for debug */ if (log.isDebugEnabled()) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        log.debug(uri);
      }
      ServerHttpRequest request = exchange.getRequest();
      String tokenId = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
      if (tokenId != null && (tokenId.startsWith("Bearer") || tokenId.startsWith("bearer"))) {
        tokenId = tokenId.substring(7);
        /* for debug */ if (log.isDebugEnabled()) log.info("tokenId: {}", tokenId);
        
        Error failCode = Error.A002;
        TokenReqDto.Verify requestBody = new TokenReqDto.Verify();
        String clientIp = XffClientIpExtractor.getClientIp(request);
        String userAgent = request.getHeaders().get(Constant.HTTP_HEADER.USER_AGENT).get(0);
        requestBody.setClientIdent(IdGenerator.createClientIdent(clientIp, userAgent));
        requestBody.setTokenId(tokenId);
        try {
          final TokenResDto.Verify vertifyDto = 
              apiClient.postForEntity(AUTH_URL, requestBody, TokenResDto.Verify.class);
          exchange = exchange.mutate()
              .request(req -> 
                req.header(HttpHeaders.AUTHORIZATION, "Bearer " + vertifyDto.getAccessToken()))
              .build();
        } catch (AccessTokenExpiredException e) {
          throw e;
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
