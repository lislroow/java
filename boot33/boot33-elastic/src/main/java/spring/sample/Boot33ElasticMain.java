package spring.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Boot33ElasticMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot33ElasticMain.class, args);
  }
  
}
