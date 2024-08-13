package spring.projectk.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.projectk.gateway.filter.HostRoutingZuulFilter;

@Configuration
public class ZuulConfig {
  
  @Bean
  HostRoutingZuulFilter hostRoutingZuulFilter() {
    return new HostRoutingZuulFilter();
  }
}
