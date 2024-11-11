package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import spring.sample.common.constant.Constant;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebMvc
@EnableFeignClients(basePackages = {Constant.BASE_PACKAGE})
public class Boot23AppMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot23AppMain.class, args);
  }
  
}
