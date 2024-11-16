package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "spring.sample")
public class Boot3xFeignMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot3xFeignMain.class, args);
  }
  
}
