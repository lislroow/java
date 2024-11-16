package spring.sample.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import spring.sample.common.constant.Constant;

@Configuration
@Slf4j
public class DataSourceH2Config {
  
  @Bean(name = Constant.DBMS.H2 + "DataSource")
  @ConfigurationProperties(prefix = "spring.sample.datasource."+Constant.DBMS.H2+".hikari")
  DataSource dataSource() throws SQLException {
    HikariDataSource hikariDataSource = DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
    hikariDataSource.setPoolName("hikari-"+Constant.DBMS.H2);
    return hikariDataSource;
  }
  
  @Bean(name = Constant.DBMS.H2 + "PlatformTransactionManager")
  PlatformTransactionManager transactionManager() throws Exception {
    PlatformTransactionManager transactionManager = null;
    transactionManager = new DataSourceTransactionManager(dataSource());
    return transactionManager;
  }
}