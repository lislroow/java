package spring.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.sample.filter.HostRoutingZuulFilter;

@Configuration
public class ZuulConfig {
  
  @Bean
  HostRoutingZuulFilter hostRoutingZuulFilter() {
    return new HostRoutingZuulFilter();
  }
}
