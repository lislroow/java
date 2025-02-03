package spring.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

@Configuration
public class JpaTransactionConfig {
  
  @Autowired LocalContainerEntityManagerFactoryBean entityManagerFactory;
  
  @Bean
  @Primary
  JpaTransactionManager jpaTransactionManager() {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
    jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());
    return jpaTransactionManager;
  }
  
}
