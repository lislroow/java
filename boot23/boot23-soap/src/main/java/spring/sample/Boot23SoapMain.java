package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ws.config.annotation.EnableWs;

@SpringBootApplication
@EnableWs
public class Boot23SoapMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot23SoapMain.class, args);
  }
  
}
