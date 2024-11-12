package spring.sample.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.util.ObjectUtils;

import spring.sample.config.mybatis.DaoSupport;

@Configuration
public class MybatisConfig {
  
  Logger log = LoggerFactory.getLogger(MybatisConfig.class);
  
  public static final String BASE_PACKAGES = "spring";
  
  @Autowired
  MybatisProperties mybatisProperties;
  
  @Autowired
  @Qualifier("lazyConnectionDataSourceProxy")
  LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy;
  
  @Autowired
  @Qualifier("mssql")
  DataSource dataSource_mssql;
  
  @Autowired
  @Qualifier("oracle")
  DataSource dataSource_oracle;
  
  // mssql
  @Bean(name = "sqlSessionFactoryBean_mssql")
  @Primary
  SqlSessionFactoryBean sqlSessionFactoryBean_mssql() throws Exception {
    String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
    log.info("[mssql] typeAliasesPackage: " + typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    // RoutingDataSource 로 runtime 시 datasource 를 결정해야 할 경우 아래 코드 사용
    // sqlSessionFactoryBean.setDataSource(lazyConnectionDataSourceProxy);
    sqlSessionFactoryBean.setDataSource(dataSource_mssql);
    
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new spring.sample.config.mybatis.PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean
  @Primary
  DaoSupport daoSupport_mssql(
      @Qualifier("sqlSessionTemplate_mssql") SqlSessionTemplate sqlSessionTemplate) throws Exception {
    return new DaoSupport(sqlSessionTemplate);
  }
  
  @Bean(name = "sqlSessionTemplate_mssql")
  @Primary
  SqlSessionTemplate sqlSessionTemplate_mssql(
      @Qualifier("sqlSessionFactoryBean_mssql") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutor_mssql")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor_mssql(
      @Qualifier("sqlSessionFactoryBean_mssql") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject(), ExecutorType.BATCH);
  }
  
  
  
  // oracle
  @Bean(name = "sqlSessionFactoryBean_oracle")
  SqlSessionFactoryBean sqlSessionFactoryBean_oracle() throws Exception {
    String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
    log.info("[oracle] typeAliasesPackage: " + typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    // RoutingDataSource 로 runtime 시 datasource 를 결정해야 할 경우 아래 코드 사용
    // sqlSessionFactoryBean.setDataSource(routingDataSource);
    sqlSessionFactoryBean.setDataSource(dataSource_oracle);
    
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new spring.sample.config.mybatis.PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean
  DaoSupport daoSupport_oracle(
      @Qualifier("sqlSessionTemplate_oracle") SqlSessionTemplate sqlSessionTemplate) throws Exception {
    return new DaoSupport(sqlSessionTemplate);
  }
  
  @Bean(name = "sqlSessionTemplate_oracle")
  SqlSessionTemplate sqlSessionTemplate_oracle(
      @Qualifier("sqlSessionFactoryBean_oracle") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutor_oracle")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor_oracle(
      @Qualifier("sqlSessionFactoryBean_oracle") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject(), ExecutorType.BATCH);
  }
}
