package spring.sample.webmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties({WebMvcProperties.class})
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  
  @Autowired
  WebMvcProperties properties;
  
  // [ for cors ]
//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**")
//        .allowedOrigins("*")
//        .allowedMethods("GET", "POST", "PUT", "DELETE"
//            , "Content-Type", "Accept-Language", "Referer")
//        //.allowCredentials(true)
//        .maxAge(3600); //  설정 seconds 동안 pre-flight 요청 caching 
//  }
  
  // [ for welcome page ]
//  @Override
//  public void addViewControllers(ViewControllerRegistry registry) {
//    registry.addViewController("/").setViewName("redirect:/jsp/main");
//  }
  
  // [ for filter ]
//  @Bean
//  FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
//    final FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<ForwardedHeaderFilter>();
//    filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
//    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    return filterRegistrationBean;
//  }
  
  // [ for listener ]
//  @Bean
//  public HttpSessionListener httpSessionListener() {
//    return new HttpSessionListener() {
//      @Override
//      public void sessionCreated(HttpSessionEvent se) {
//        System.out.println("Session Created with session id+" + se.getSession().getId());
//      }
//      @Override
//      public void sessionDestroyed(HttpSessionEvent se) {
//        System.out.println("Session Destroyed, Session id:" + se.getSession().getId());
//      }
//    };
//  }

  // [ for using webjars js ]
//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/webjars/**")
//            .addResourceLocations("/webjars/")
//            .resourceChain(false);
//    registry.setOrder(1);
//  }
  
}
