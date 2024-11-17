### 99. snippets

#### 2) isUseJdbc4Validation

- JDBC 4 유효성 검사(isUseJdbc4Validation = true): 간단하고 효율적이지만, 드라이버 지원 여부 확인 필요.
- validationQuery(isUseJdbc4Validation = false): 드라이버 지원 문제가 있을 때 유효성을 검사하는 대안.

```java
package com.zaxxer.hikari;
public class HikariConfig implements HikariConfigMXBean {
  PoolBase(final HikariConfig config) {
    this.config = config;
    // spring.datasource.hikari.connection-test-query 설정이 없으면 jdbc4 validation 을 사용함
    this.isUseJdbc4Validation = config.getConnectionTestQuery() == null;
```


#### 1) RoutingDataSource

```java
@Configuration
public class DataSourceConfig {

  @Primary
  @Bean(name = "mssql")
  @ConfigurationProperties(prefix = "sample.jdbc.datasource.mssql")
  DataSource dataSource_mssql() throws SQLException {
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
  }
  
  @Bean(name = "oracle")
  @ConfigurationProperties(prefix = "sample.jdbc.datasource.oracle")
  DataSource dataSource_oracle() throws SQLException {
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
  }
  
  @Bean
  RoutingDataSource routingDataSource(
      @Qualifier("mssql") DataSource mssqlDatasource,
      @Qualifier("oracle") DataSource oracleDatasource) {
    RoutingDataSource routingDataSource = new RoutingDataSource();
    Map<Object, Object> dataSources = new HashMap<Object, Object>();
    dataSources.put("mssql", mssqlDatasource);
    dataSources.put("oracle", oracleDatasource);
    routingDataSource.setTargetDataSources(dataSources);
    routingDataSource.setDefaultTargetDataSource(mssqlDatasource);
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
      // mssql datasource 를 기본값으로 설정했습니다.
      String routingKey = "mssql";
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
```

#### 2) jdbc-url

```
# mssql
jdbc:sqlserver://IP주소:1433;DatabaseName=데이터베이스명
```


#### 3) DataSourceInitializer


```java
@Configuration
@Slf4j
public class DataSourceConfig {
  @Value("classpath:init-h2.sql")
  private org.springframework.core.io.Resource initScript;
  
  @Bean("dataSourceInitializer")
  @ConditionalOnProperty(name = "spring.sample.datasource.h2.init", havingValue = "true", matchIfMissing = false)
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(initScript);
    initializer.setDatabasePopulator(populator);
    return initializer;
  }
}
```