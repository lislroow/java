package spring.custom.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.DBMS_TYPE;
import spring.custom.common.mybatis.PagingInterceptor;
import spring.custom.config.conditions.MybatisMariaEnableCondition;
import spring.custom.config.properties.MybatisProperties;

@Configuration
@Conditional(MybatisMariaEnableCondition.class)
@EnableConfigurationProperties(spring.custom.config.properties.MybatisProperties.class)
@Slf4j
public class MybatisMariaConfig {
  
  @Autowired
  spring.custom.config.properties.MybatisProperties mybatisProperties;
  
  @Autowired
  @Qualifier(Constant.DBMS.MARIA + "DataSource")
  DataSource dataSource;
  
  @Bean(name = Constant.DBMS.MARIA + Constant.BEAN.SQL_SESSION_FACTORY_BEAN)
  SqlSessionFactoryBean sqlSessionFactoryBean() {
    MybatisProperties.Configure config = mybatisProperties.getConfigure(DBMS_TYPE.MARIA);
    String typeAliasesPackage = config.getTypeAliasesPackage();
    log.info("[mybatis] mybatis.type-aliases-package: {}", typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setMapperLocations(config.resolveMapperLocations());
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setJdbcTypeForNull(config.getJdbcTypeForNull());
    configuration.setMapUnderscoreToCamelCase(config.getMapUnderscoreToCamelCase());
    sqlSessionFactoryBean.setConfiguration(configuration);
    
    sqlSessionFactoryBean.setPlugins(new PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean(name = Constant.DBMS.MARIA + "SqlSessionTemplate")
  SqlSessionTemplate sqlSessionTemplate() throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
  }
  
  @Bean(name = Constant.DBMS.MARIA + "SqlSessionTemplateForBatchExecutor")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject(), ExecutorType.BATCH);
  }
}
