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
import org.springframework.util.ObjectUtils;

import spring.sample.common.mybatis.DaoSupport;

@Configuration
public class MybatisConfig {
  
  Logger log = LoggerFactory.getLogger(MybatisConfig.class);
  
  public static final String BASE_PACKAGES = "spring";

  //@Autowired
  //LazyConnectionDataSourceProxy dataSource;
  @Autowired
  DataSource dataSource;
  
  @Autowired
  MybatisProperties mybatisProperties;
  
  @Bean
  SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
    String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
    log.info("typeAliasesPackage: " + typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    sqlSessionFactoryBean.setConfiguration(mybatisProperties.getConfiguration());
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new spring.sample.common.mybatis.PagingInterceptor());
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
