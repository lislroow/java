
### webmvc 기능

#### 1) cors

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE"
            , "Content-Type", "Accept-Language", "Referer")
        .allowCredentials(true)
        .maxAge(3600); //  설정 seconds 동안 pre-flight 요청 caching 
  }
}
```

#### 2) welcome page

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:/jsp/main");
  }
}
```

#### 3) user filter

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Bean
  FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
    final FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<ForwardedHeaderFilter>();
    filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return filterRegistrationBean;
  }
}
```

#### 4) listener

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Bean
  public HttpSessionListener httpSessionListener() {
    return new HttpSessionListener() {
      @Override
      public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created with session id+" + se.getSession().getId());
      }
      @Override
      public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session Destroyed, Session id:" + se.getSession().getId());
      }
    };
  }
}
```

#### 5) resource handler

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("/webjars/")
            .resourceChain(false);
    registry.setOrder(1);
  }
}
```
