package spring.sample.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
import spring.sample.common.constant.Constant;
import spring.sample.common.enumcode.DBMS_TYPE;
import spring.sample.common.mybatis.DaoSupport;
import spring.sample.config.properties.MybatisProperties;

@Configuration
@ConditionalOnProperty(
    prefix = "spring.sample.mybatis."+Constant.DBMS.H2, name = Constant.ENABLED,
    havingValue = "true",
    matchIfMissing = false)
@Slf4j
public class MybatisH2Config {
  
  @Autowired
  spring.sample.config.properties.MybatisProperties mybatisProperties;
  
  @Autowired
  @Qualifier(value = Constant.DBMS.H2 + "DataSource")
  DataSource dataSource;
  
  @Bean(name = Constant.DBMS.H2 + MybatisProperties.SQL_SESSION_FACTORY_BEAN)
  SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
    MybatisProperties.Configure config = mybatisProperties.getConfigure(DBMS_TYPE.H2);
    String typeAliasesPackage = config.getTypeAliasesPackage();
    log.info("[mybatis] mybatis.type-aliases-package: {}", typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setMapperLocations(config.resolveMapperLocations());
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    
    // ---
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setJdbcTypeForNull(config.getJdbcTypeForNull());
    configuration.setMapUnderscoreToCamelCase(config.getMapUnderscoreToCamelCase());
    sqlSessionFactoryBean.setConfiguration(configuration);
    // ---
    
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new spring.sample.common.mybatis.PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean(name = Constant.DBMS.H2 + "SqlSessionTemplate")
  SqlSessionTemplate sqlSessionTemplate() throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
  }
  
  @Bean(name = Constant.DBMS.H2 + "SqlSessionTemplateForBatchExecutor")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject(), ExecutorType.BATCH);
  }
  
  @Bean(name = Constant.DBMS.H2 + "DaoSupport")
  DaoSupport daoSupport() throws Exception {
    return new DaoSupport(sqlSessionTemplate());
  }
}
