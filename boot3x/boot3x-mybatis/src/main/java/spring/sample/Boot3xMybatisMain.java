package spring.sample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Boot3xMybatisMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot3xMybatisMain.class, args);
  }
  
}
