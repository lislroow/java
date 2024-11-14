package spring.sample.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import spring.sample.common.annotation.MapperH2;
import spring.sample.common.annotation.MapperMaria;
import spring.sample.common.annotation.MapperOracle;
import spring.sample.common.bean.BeanNameGenerator;
import spring.sample.common.constant.Constant;
import spring.sample.common.enumcode.DBMS_CODE;
import spring.sample.config.properties.MybatisMultipleProperties;

@Configuration
@EnableConfigurationProperties(MybatisMultipleProperties.class)
@Slf4j
public class MybatisConfig {

  @Bean(name = Constant.DBMS.H2 + "MapperScannerConfigurer")
  MapperScannerConfigurer h2MapperScannerConfigurer() {
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(Constant.DBMS.H2 + MybatisMultipleProperties.SQL_SESSION_FACTORY_BEAN);
    scannerConfigurer.setNameGenerator(new BeanNameGenerator() {
      @Override
      public String generateBeanName(
          org.springframework.beans.factory.config.BeanDefinition definition,
          org.springframework.beans.factory.support.BeanDefinitionRegistry registry) {
        
        String daoName = definition.getBeanClassName();
        if (daoName.lastIndexOf(MybatisMultipleProperties.DAO) > -1) {
          daoName = daoName.substring(daoName.lastIndexOf('.') + 1);
          daoName = daoName.substring(0, 1).toLowerCase() + daoName.substring(1);
          daoName = daoName.substring(0, daoName.lastIndexOf(MybatisMultipleProperties.DAO)) + DBMS_CODE.H2.capital() + MybatisMultipleProperties.DAO;
        }
        definition.setPrimary(true);
        log.info("[mybatis][mapper-scanner][{}] {}", DBMS_CODE.H2.code(), daoName);
        return daoName;
      }
    });
    scannerConfigurer.setAnnotationClass(MapperH2.class);
    return scannerConfigurer;
  }

  @Bean(Constant.DBMS.MARIA + "MapperScannerConfigurer")
  MapperScannerConfigurer mariaMapperScannerConfigurer() {
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(Constant.DBMS.MARIA + MybatisMultipleProperties.SQL_SESSION_FACTORY_BEAN);
    scannerConfigurer.setNameGenerator(new BeanNameGenerator() {
      @Override
      public String generateBeanName(
          org.springframework.beans.factory.config.BeanDefinition definition,
          org.springframework.beans.factory.support.BeanDefinitionRegistry registry) {
        
        String daoName = definition.getBeanClassName();
        if (daoName.lastIndexOf(MybatisMultipleProperties.DAO) > -1) {
          daoName = daoName.substring(daoName.lastIndexOf('.') + 1);
          daoName = daoName.substring(0, 1).toLowerCase() + daoName.substring(1);
          daoName = daoName.substring(0, daoName.lastIndexOf(MybatisMultipleProperties.DAO)) + DBMS_CODE.MARIA.capital() + MybatisMultipleProperties.DAO;
        }
        log.info("[mybatis][mapper-scanner][{}] {}", DBMS_CODE.MARIA.code(), daoName);
        return daoName;
      }
    });
    scannerConfigurer.setAnnotationClass(MapperMaria.class);
    return scannerConfigurer;
  }
  
  @Bean(Constant.DBMS.ORACLE + "MapperScannerConfigurer")
  MapperScannerConfigurer oracleMapperScannerConfigurer() {
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(Constant.DBMS.ORACLE + MybatisMultipleProperties.SQL_SESSION_FACTORY_BEAN);
    scannerConfigurer.setNameGenerator(new BeanNameGenerator() {
      @Override
      public String generateBeanName(
          org.springframework.beans.factory.config.BeanDefinition definition,
          org.springframework.beans.factory.support.BeanDefinitionRegistry registry) {
        
        String daoName = definition.getBeanClassName();
        if (daoName.lastIndexOf(MybatisMultipleProperties.DAO) > -1) {
          daoName = daoName.substring(daoName.lastIndexOf('.') + 1);
          daoName = daoName.substring(0, 1).toLowerCase() + daoName.substring(1);
          daoName = daoName.substring(0, daoName.lastIndexOf(MybatisMultipleProperties.DAO)) + DBMS_CODE.ORACLE.capital() + MybatisMultipleProperties.DAO;
        }
        log.info("[mybatis][mapper-scanner][{}] {}", DBMS_CODE.ORACLE.code(), daoName);
        return daoName;
      }
    });
    scannerConfigurer.setAnnotationClass(MapperOracle.class);
    return scannerConfigurer;
  }
  
}