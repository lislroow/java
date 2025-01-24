package spring.custom.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import spring.custom.code.EnumScientist.FieldOfStudy;
//import spring.custom.common.enumcode.EnumCodeTypeArgumentResolver;
//import spring.custom.common.enumcode.StringToEnumCodeTypeConverter;
//import spring.custom.common.enumcode.StringToEnumConverter;
//import spring.custom.common.enumcode.StringToFieldOfStudyConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    /* dead code */ //registry.addConverter(new StringToFieldOfStudyConverter());
    /* dead code */ //registry.addConverter(new StringToEnumCodeTypeConverter<>(FieldOfStudy.class));
    /* dead code */ //registry.addConverter(new StringToEnumConverter<>(FieldOfStudy.class));
  }
  
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    /* dead code */ //resolvers.add(0, new EnumCodeTypeArgumentResolver());
  }

}
