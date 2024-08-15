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
    return new ServletRegistrationBean<>(servlet, "/webservice/*");
  }
  
  @Bean(name = "hello")
  DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema helloSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("HelloPort");
    wsdl11Definition.setLocationUri("/webservice/hello");
    wsdl11Definition.setTargetNamespace("http://ws.mgkim.net/hello");
    wsdl11Definition.setSchema(helloSchema);
    return wsdl11Definition;
  }
  
  @Bean(name = "member")
  DefaultWsdl11Definition defaultWsdl11Definition_member(XsdSchema memberSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("MemberPort");
    wsdl11Definition.setLocationUri("/webservice/member");
    wsdl11Definition.setTargetNamespace("http://ws.mgkim.net/member");
    wsdl11Definition.setSchema(memberSchema);
    return wsdl11Definition;
  }
  
  @Bean
  XsdSchema helloSchema() {
    return new SimpleXsdSchema(new ClassPathResource("/xsd/hello.xsd"));
  }
  
  @Bean
  XsdSchema memberSchema() {
    return new SimpleXsdSchema(new ClassPathResource("/xsd/member.xsd"));
  }
  // --for web-services
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/xsd/**")
      .addResourceLocations("classpath:/xsd/");
  }
}
