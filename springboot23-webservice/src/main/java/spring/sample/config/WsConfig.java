package spring.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

@Configuration
@EnableWs
public class WsConfig extends WsConfigurerAdapter {
  
}
