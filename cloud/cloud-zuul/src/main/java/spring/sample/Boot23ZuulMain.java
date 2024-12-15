package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Boot23ZuulMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot23ZuulMain.class, args);
  }
  
}
