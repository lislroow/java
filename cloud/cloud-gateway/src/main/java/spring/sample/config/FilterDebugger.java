package spring.sample.config;

import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.ApplicationContext;

//@Component
public class FilterDebugger {

  public FilterDebugger(ApplicationContext context) {
    context.getBeansOfType(GatewayFilterFactory.class).forEach((name, factory) -> {
      System.out.println("Registered GatewayFilterFactory: " + name);
    });
  }
  
}
