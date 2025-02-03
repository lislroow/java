package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableWebSecurity
@EnableTransactionManagement
@EnableJpaRepositories
@EnableFeignClients
@EnableScheduling
public class SchedulerMain {
  
  public static void main(String[] args) {
    SpringApplication.run(SchedulerMain.class, args);
  }

}
