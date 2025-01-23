package spring.custom.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
  
//  final org.springframework.boot.autoconfigure.orm.jpa.JpaProperties jpaProperties;
  
  @Autowired
  @Qualifier("dataSource")
  private DataSource dataSourcePrimary;
  
}
