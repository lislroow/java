package spring.sample.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import spring.sample.common.constant.Constant;

@Configuration
public class DataSourceMariaConfig {
  
  @Bean(name = Constant.DBMS.MARIA + "DataSource")
  @ConfigurationProperties(prefix = "spring.sample.datasource."+Constant.DBMS.MARIA+".hikari")
  DataSource dataSource() {
    HikariDataSource hikariDataSource = DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
    hikariDataSource.setPoolName("hikari-"+Constant.DBMS.MARIA);
    return hikariDataSource;
  }
  
  @Bean(name = Constant.DBMS.MARIA + "PlatformTransactionManager")
  PlatformTransactionManager transactionManager() {
    PlatformTransactionManager transactionManager = null;
    transactionManager = new DataSourceTransactionManager(dataSource());
    return transactionManager;
  }
  
  @Value("classpath:init-"+Constant.DBMS.MARIA+".sql")
  private org.springframework.core.io.Resource initScript;
  
  @Bean(name = Constant.DBMS.MARIA + "DataSourceInitializer")
  @ConditionalOnProperty(name = "spring.sample.datasource." + Constant.DBMS.MARIA + ".init", havingValue = "true", matchIfMissing = false)
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(initScript);
    initializer.setDatabasePopulator(populator);
    return initializer;
  }
}
