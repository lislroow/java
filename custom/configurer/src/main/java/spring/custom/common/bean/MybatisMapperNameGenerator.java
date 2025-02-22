package spring.custom.common.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;
import spring.custom.common.syscode.DBMS;

@Slf4j
public class MybatisMapperNameGenerator extends AnnotationBeanNameGenerator {
  
  private final DBMS dbmsType;
  
  public MybatisMapperNameGenerator(DBMS dbmsType) {
    this.dbmsType = dbmsType;
  }
  
  @Override
  public String generateBeanName(
      org.springframework.beans.factory.config.BeanDefinition definition,
      org.springframework.beans.factory.support.BeanDefinitionRegistry registry) {
    
    String beanClassName = definition.getBeanClassName();
    String daoName = beanClassName;
    if (daoName.lastIndexOf(Constant.BEAN.DAO) > -1) {
      daoName = daoName.substring(daoName.lastIndexOf('.') + 1);
      daoName = daoName.substring(0, 1).toLowerCase() + daoName.substring(1);
      String entityName = daoName.substring(0, daoName.lastIndexOf(Constant.BEAN.DAO));
      daoName = entityName + dbmsType.capital() + Constant.BEAN.DAO;
    }
    definition.setPrimary(dbmsType.isPrimary());
    log.info("[mybatis-mapper-scanner][primary: {}][{}] {}", dbmsType.isPrimary(), dbmsType.code(), daoName);
    return daoName;
  }
  
  @Override
  protected String buildDefaultBeanName(BeanDefinition definition) {
    return definition.getBeanClassName();
  }
}