### 99. snippets

#### 1) RoutingDataSource

```java
@Configuration
public class DataSourceConfig {
  @Bean
  RoutingDataSource routingDataSource(
      @Qualifier("h2") DataSource h2Datasource) {
    RoutingDataSource routingDataSource = new RoutingDataSource();
    Map<Object, Object> dataSources = new HashMap<Object, Object>();
    dataSources.put("h2", h2Datasource);
    routingDataSource.setTargetDataSources(dataSources);
    return routingDataSource;
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
      // 특정 사용자, 특정 서비스 등에 따라 datasource 가 결정되도록 할 수 있습니다.
      // h2 datasource 를 기본값으로 설정했습니다.
      String routingKey = "h2";
      return routingKey;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
      Connection conn = determineTargetDataSource().getConnection();
      return conn;
    }
  }
}
```

#### 2) jdbc-url

```
# mssql
jdbc:sqlserver://IP주소:1433;DatabaseName=데이터베이스명
```