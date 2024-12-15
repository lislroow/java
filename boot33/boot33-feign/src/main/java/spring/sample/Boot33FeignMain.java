package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "spring.sample")
public class Boot33FeignMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot33FeignMain.class, args);
  }
  
}
