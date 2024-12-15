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

import spring.sample.common.constant.Constant;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  
  // for web-services
  @Bean
  ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
    ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, Constant.APP.BASE_URI+"/*");
  }
  
  public static final String NS_SAY_HELLO = "http://soap.mgkim.net"+Constant.APP.BASE_URI+"/SayHello/types";
  public static final String NS_MEMBER_DETAIL = "http://soap.mgkim.net"+Constant.APP.BASE_URI+"/MemberDetail/types";
  
  @Bean(name = "hello")
  DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema helloSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("HelloPort");
    wsdl11Definition.setLocationUri(Constant.APP.BASE_URI+"/SayHello/types");
    wsdl11Definition.setTargetNamespace("http://soap.mgkim.net"+Constant.APP.BASE_URI+"/SayHello/types");
    wsdl11Definition.setSchema(helloSchema);
    return wsdl11Definition;
  }
  
  @Bean(name = "member")
  DefaultWsdl11Definition defaultWsdl11Definition_member(XsdSchema memberSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("MemberPort");
    wsdl11Definition.setLocationUri(Constant.APP.BASE_URI+"/MemberDetail/types");
    wsdl11Definition.setTargetNamespace("http://soap.mgkim.net"+Constant.APP.BASE_URI+"/MemberDetail/types");
    wsdl11Definition.setSchema(memberSchema);
    return wsdl11Definition;
  }
  
  @Bean
  XsdSchema helloSchema() {
    return new SimpleXsdSchema(new ClassPathResource("/xsd/SayHello.xsd"));
  }
  
  @Bean
  XsdSchema memberSchema() {
    return new SimpleXsdSchema(new ClassPathResource("/xsd/MemberDetail.xsd"));
  }
  // --for web-services
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/xsd/**")
      .addResourceLocations("classpath:/xsd/");
  }
}
