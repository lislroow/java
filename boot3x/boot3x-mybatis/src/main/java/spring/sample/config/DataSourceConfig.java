package spring.sample.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import spring.sample.common.constant.Constant;

@Configuration
@Slf4j
public class DataSourceConfig {
  
  @Bean
  RoutingDataSource routingDataSource(
      @Qualifier(Constant.DBMS.H2 + "DataSource") DataSource datasourceH2,
      @Qualifier(Constant.DBMS.ORACLE + "DataSource") DataSource datasourceOracle) {
    
    RoutingDataSource routingDatasource = new RoutingDataSource();
    Map<Object, Object> datasourceMap = new HashMap<Object, Object>();
    datasourceMap.put(Constant.DBMS.H2, datasourceH2);
    datasourceMap.put(Constant.DBMS.ORACLE, datasourceOracle);
    
    routingDatasource.setTargetDataSources(datasourceMap);
    routingDatasource.setDefaultTargetDataSource(datasourceH2);
    return routingDatasource;
  }
  
  class RoutingDataSource extends AbstractRoutingDataSource {
    
    private DataSource datasource;
    private boolean lenientFallback = true;
    @Override
    protected DataSource determineTargetDataSource() {
      Assert.notNull(getResolvedDataSources(), "DataSource router not initialized");
      Object lookupKey = determineCurrentLookupKey();
      DataSource dataSource = getResolvedDataSources().get(lookupKey);
      if (dataSource == null && (lenientFallback || lookupKey == null)) {
        dataSource = getResolvedDefaultDataSource();
        log.info("defaultTarget: " + ((HikariDataSource) dataSource).getJdbcUrl());
      } else {
        log.info("determineTarget: " + ((HikariDataSource) dataSource).getJdbcUrl());
      }
      return dataSource;
    }
    
    void setFallbackTargetDataSource(DataSource datasource) {
      this.datasource = datasource;
    }
    
    @Override
    protected Object determineCurrentLookupKey() {
      String routingKey = Constant.DBMS.H2;
      return routingKey;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
      Connection conn = determineTargetDataSource().getConnection();
      return conn;
    }
  }
  
  @Bean(name = "lazyConnectionDataSourceProxy")
  LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy(
      RoutingDataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }
}
