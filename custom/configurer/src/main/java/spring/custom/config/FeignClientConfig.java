package spring.custom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import spring.custom.common.constant.Constant;

@Configuration
public class FeignClientConfig {

  @Bean
  RequestInterceptor xForwardedForInterceptor() {
    return new RequestInterceptor() {
      @Override
      public void apply(RequestTemplate template) {
        HttpServletRequest originRequest = null;
        originRequest = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String xffHeader = originRequest.getHeader(Constant.HTTP_HEADER.X_FORWARDED_FOR);
        if (xffHeader != null) {
          template.header(Constant.HTTP_HEADER.X_FORWARDED_FOR, xffHeader);
        }
      }
    };
  }
  
}
