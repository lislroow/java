package spring.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import spring.auth.common.constant.AuthApiConstant;

@Configuration
public class SwaggerConfig {
  
  @Bean
  OpenAPI openAPI() {
    return new OpenAPI().addServersItem(new Server().url(AuthApiConstant.APP.AUTH_URI));
  }
  
}
