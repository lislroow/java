package spring.cloud.config;

import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GatewayFilterDebug {

  public GatewayFilterDebug(ApplicationContext context) {
    context.getBeansOfType(GatewayFilterFactory.class).forEach((name, factory) -> {
      /* for debug */ log.debug("filter bean: {}", name);
    });
  }
}
