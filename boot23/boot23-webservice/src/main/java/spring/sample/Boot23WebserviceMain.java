package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ws.config.annotation.EnableWs;

@SpringBootApplication
@EnableWs
public class Boot23WebserviceMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot23WebserviceMain.class, args);
  }
  
}
