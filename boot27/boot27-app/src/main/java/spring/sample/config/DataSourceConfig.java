package spring.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.util.Assert;
//
//import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
  
  Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
  
//  @Bean
//  RoutingDataSource routingDataSource(DataSource mainDatasource) {
//    RoutingDataSource routingDataSource = new RoutingDataSource();
//    Map<Object, Object> dataSources = new HashMap<Object, Object>();
//    dataSources.put("main", mainDatasource);
//    routingDataSource.setTargetDataSources(dataSources);
//    routingDataSource.setDefaultTargetDataSource(mainDatasource);
//    return routingDataSource;
//  }
//  
//  class RoutingDataSource extends AbstractRoutingDataSource {
//    private DataSource datasource;
//    private boolean lenientFallback = true;
//    @Override
//    protected DataSource determineTargetDataSource() {
//      Assert.notNull(getResolvedDataSources(), "DataSource router not initialized");
//      Object lookupKey = determineCurrentLookupKey();
//      DataSource dataSource = getResolvedDataSources().get(lookupKey);
//      if (dataSource == null && (lenientFallback || lookupKey == null)) {
//        dataSource = getResolvedDefaultDataSource();
//        log.info("defaultTarget: " + ((HikariDataSource) dataSource).getJdbcUrl());
//      } else {
//        log.info("determineTarget: " + ((HikariDataSource) dataSource).getJdbcUrl());
//      }
//      return dataSource;
//    }
//    
//    void setFallbackTargetDataSource(DataSource datasource) {
//      this.datasource = datasource;
//    }
//    
//    @Override
//    protected Object determineCurrentLookupKey() {
//      String routingKey = "main";
//      return routingKey;
//    }
//    
//    @Override
//    public Connection getConnection() throws SQLException {
//      Connection conn = determineTargetDataSource().getConnection();
//      return conn;
//    }
//  }
//  
//  @Primary
//  @Bean
//  LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy(
//      @Qualifier RoutingDataSource routingDataSource) {
//    return new LazyConnectionDataSourceProxy(routingDataSource);
//  }
}
