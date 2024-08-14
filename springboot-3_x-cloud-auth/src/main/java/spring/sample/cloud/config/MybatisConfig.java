package spring.sample.cloud.config;

import javax.sql.DataSource;

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
import org.springframework.util.ObjectUtils;

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
  MybatisProperties properties;
  
  @Autowired
  @Qualifier("lazyConnectionDataSourceProxy")
  LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy;
  
  @Autowired
  @Qualifier("h2")
  DataSource dataSource_h2;
  
  // h2
  @Bean(name = "sqlSessionFactoryBean_h2")
  @Primary
  SqlSessionFactoryBean sqlSessionFactoryBean_h2() throws Exception {
    String configFile = properties.getConfigFile();
    String mapperLocation = properties.getMapperLocation();
    String typeAliasesPackage = properties.getTypeAliasesPackage();
    log.info("[h2] configFile: " + configFile);
    log.info("[h2] mapperLocation: " + mapperLocation);
    log.info("[h2] typeAliasesPackage: " + typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource_h2);
    
    sqlSessionFactoryBean.setConfigLocation(
        new PathMatchingResourcePatternResolver().getResource(configFile));
    sqlSessionFactoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources(mapperLocation));
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    return sqlSessionFactoryBean;
  }
  
  @Bean(name = "sqlSessionTemplate_h2")
  @Primary
  SqlSessionTemplate sqlSessionTemplate_h2(
      @Qualifier("sqlSessionFactoryBean_h2") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
}
