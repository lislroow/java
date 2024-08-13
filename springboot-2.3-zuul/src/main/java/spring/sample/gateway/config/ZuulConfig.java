package spring.sample.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.sample.gateway.filter.HostRoutingZuulFilter;

@Configuration
public class ZuulConfig {
  
  @Bean
  HostRoutingZuulFilter hostRoutingZuulFilter() {
    return new HostRoutingZuulFilter();
  }
}
