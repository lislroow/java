package spring.sample.mybatis.config;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import spring.sample.mybatis.config.mybatis.DaoSupport;

@Configuration
@Conditional(MybatisProperties.class)
@EnableConfigurationProperties({MybatisProperties.class})
@MapperScan(basePackages = MybatisConfig.BASE_PACKAGES,
            annotationClass = org.apache.ibatis.annotations.Mapper.class
)
public class MybatisConfig {
  
  Logger log = LoggerFactory.getLogger(MybatisConfig.class);
  
  public static final String BASE_PACKAGES = "spring";

  @Autowired
  LazyConnectionDataSourceProxy dataSource;
  
  @Autowired
  MybatisProperties properties;
  
  @Bean
  SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
    log.info("MapperScan, TypeAliasesPackage: " + BASE_PACKAGES);
    String configFile = properties.getConfigFile();
    String mapperLocation = properties.getMapperLocation();
    log.info("configFile: " + configFile);
    log.info("mapperLocation: " + mapperLocation);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setConfigLocation(
        new PathMatchingResourcePatternResolver().getResource(configFile));
    sqlSessionFactoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources(mapperLocation));
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    sqlSessionFactoryBean.setTypeAliasesPackage(BASE_PACKAGES);
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new spring.sample.mybatis.config.mybatis.PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean
  DaoSupport daoSupport(
      @Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) throws Exception {
    return new DaoSupport(sqlSessionTemplate);
  }
  
  @Primary
  @Bean(name = "sqlSessionTemplate")
  SqlSessionTemplate sqlSessionTemplate(
      SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutor")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor(
      SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject(), ExecutorType.BATCH);
  }
}
