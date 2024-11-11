package spring.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import spring.sample.config.MybatisConfig;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(
    basePackages = MybatisConfig.BASE_PACKAGES,
    annotationClass = org.apache.ibatis.annotations.Mapper.class)
@EnableWebMvc
public class Boot23SoapclientMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot23SoapclientMain.class, args);
  }
  
}
