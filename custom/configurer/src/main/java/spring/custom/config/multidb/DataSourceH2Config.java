package spring.custom.config.multidb;

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

import spring.custom.common.constant.Constant;

@Configuration
@ConditionalOnProperty(prefix = Constant.CUSTOM+".datasource."+Constant.DBMS_TYPE.H2 + ".hikari", name = Constant.ENABLED, havingValue = "true", matchIfMissing = false)
public class DataSourceH2Config {
  
  @Bean(name = Constant.DBMS_TYPE.H2 + "DataSource")
  @ConfigurationProperties(prefix = Constant.CUSTOM+".datasource."+Constant.DBMS_TYPE.H2+".hikari")
  DataSource dataSource() {
    HikariDataSource hikariDataSource = DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
    hikariDataSource.setPoolName("hikari-"+Constant.DBMS_TYPE.H2);
    return hikariDataSource;
  }
  
  @Bean(name = Constant.DBMS_TYPE.H2 + "PlatformTransactionManager")
  PlatformTransactionManager transactionManager() {
    PlatformTransactionManager transactionManager = null;
    transactionManager = new DataSourceTransactionManager(dataSource());
    return transactionManager;
  }
  
  @Value("classpath:sql/init-"+Constant.DBMS_TYPE.H2+".sql")
  private org.springframework.core.io.Resource initScript;
  
  @Bean(name = Constant.DBMS_TYPE.H2 + "DataSourceInitializer")
  @ConditionalOnProperty(name = "custom.datasource." + Constant.DBMS_TYPE.H2 + ".init", havingValue = "true", matchIfMissing = false)
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(initScript);
    initializer.setDatabasePopulator(populator);
    return initializer;
  }
}
