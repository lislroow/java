package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableWebMvc
public class Boot23EgressMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot23EgressMain.class, args);
  }
  
}
