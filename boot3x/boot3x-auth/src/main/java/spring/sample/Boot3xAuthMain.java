package spring.sample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSecurity
public class Boot3xAuthMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot3xAuthMain.class, args);
  }
  
}
