package spring.custom.config;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;
import spring.custom.common.mybatis.PagingInterceptor;

@Configuration
@ConditionalOnProperty(prefix = "mybatis", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
@EnableConfigurationProperties(org.mybatis.spring.boot.autoconfigure.MybatisProperties.class)
@Slf4j
@MapperScan(basePackages = {Constant.BASE_PACKAGE}, annotationClass = Mapper.class, sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class MybatisConfig {
  
  @Autowired
  org.mybatis.spring.boot.autoconfigure.MybatisProperties mybatisProperties;
  
  @Autowired
  @Qualifier(value = "dataSource")
  DataSource dataSource;
  
  @Bean(name = "sqlSessionFactoryBean")
  SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
    org.mybatis.spring.boot.autoconfigure.MybatisProperties.CoreConfiguration config = 
        mybatisProperties.getConfiguration();
    String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
    log.info("[mybatis] mybatis.type-aliases-package: {}", typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
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
  
  @Bean(name = "sqlSessionTemplate")
  SqlSessionTemplate sqlSessionTemplate() throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutor")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject(), ExecutorType.BATCH);
  }
}
