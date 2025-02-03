package spring.custom.config;

import javax.sql.DataSource;

//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

//import com.zaxxer.hikari.HikariDataSource;

//import spring.custom.common.constant.Constant;

@Configuration
//@ConditionalOnProperty(prefix = "spring.datasource.hikari", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
public class DataSourceConfig {

  //@Bean(name = "dataSource")
  //@ConfigurationProperties(prefix = "spring.datasource.hikari")
  //DataSource dataSource() {
  //  HikariDataSource hikariDataSource = DataSourceBuilder.create()
  //      .type(HikariDataSource.class)
  //      .build();
  //  return hikariDataSource;
  //}
  
//  @Bean
//  PlatformTransactionManager transactionManager(DataSource dataSource) {
//    PlatformTransactionManager transactionManager = null;
//    transactionManager = new DataSourceTransactionManager(dataSource);
//    return transactionManager;
//  }
  
//  @Value("classpath:init-h2.sql")
//  private org.springframework.core.io.Resource initScript;
//  
//  @Bean(name = "dataSourceInitializer")
//  @ConditionalOnProperty(name = "spring.datasource.init", havingValue = "false", matchIfMissing = true)
//  DataSourceInitializer dataSourceInitializer() {
//    DataSourceInitializer initializer = new DataSourceInitializer();
//    initializer.setDataSource(dataSource());
//    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//    populator.addScript(initScript);
//    initializer.setDatabasePopulator(populator);
//    return initializer;
//  }
}
