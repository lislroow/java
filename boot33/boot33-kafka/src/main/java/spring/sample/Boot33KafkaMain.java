package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Boot33KafkaMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot33KafkaMain.class, args);
  }
  
}
