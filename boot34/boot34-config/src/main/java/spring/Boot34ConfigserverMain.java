package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Boot34ConfigserverMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot34ConfigserverMain.class, args);
  }
  
}
