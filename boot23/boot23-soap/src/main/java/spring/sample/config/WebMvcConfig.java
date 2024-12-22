package spring.sample.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  
  // for web-services
  @Bean
  ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
    ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, "/v1/*"); // uri pattern
  }
  
  public static final class NAMESPACES {
    public static final String QUALIFICATION = "https://soap.mgkim.net/v1/qualification/types";
  }
  
  @Bean(name = "qualification")
  DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema helloSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("QualificationPort");
    wsdl11Definition.setLocationUri("/v1/qualification/types");
    wsdl11Definition.setTargetNamespace(NAMESPACES.QUALIFICATION);
    wsdl11Definition.setSchema(helloSchema);
    return wsdl11Definition;
  }
  
  @Bean
  XsdSchema helloSchema() {
    return new SimpleXsdSchema(new ClassPathResource("/soap/qualification.xsd"));
  }
  // --for web-services
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/soap/**")
      .addResourceLocations("classpath:/soap/");
  }
}
