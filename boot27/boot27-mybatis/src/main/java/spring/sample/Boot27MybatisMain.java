package spring.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.sample.config.MybatisConfig;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(
    basePackages = MybatisConfig.BASE_PACKAGES,
    annotationClass = org.apache.ibatis.annotations.Mapper.class)
@EnableTransactionManagement
public class Boot27MybatisMain {
  
  public static void main(String[] args) {
    SpringApplication.run(Boot27MybatisMain.class, args);
  }
  
}
