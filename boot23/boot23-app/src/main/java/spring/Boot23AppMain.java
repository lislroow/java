package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import spring.sample.common.constant.Constant;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {Constant.BASE_PACKAGE, "mgkim"})
public class Boot23AppMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot23AppMain.class, args);
  }
  
}
