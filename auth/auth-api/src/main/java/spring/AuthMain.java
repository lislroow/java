package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableWebSecurity
@EnableTransactionManagement
//@EnableJpaRepositories
//@EntityScan
//@EnableFeignClients
public class AuthMain {
  
  public static void main(String[] args) {
    SpringApplication.run(AuthMain.class, args);
  }

}
