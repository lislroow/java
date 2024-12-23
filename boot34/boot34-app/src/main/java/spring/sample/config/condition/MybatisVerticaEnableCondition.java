package spring.sample.config.condition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import lombok.extern.slf4j.Slf4j;
import spring.sample.common.constant.Constant;
import spring.sample.config.MapperScannerConfig;

@Slf4j
public class MybatisVerticaEnableCondition implements Condition {

  public static final String FILE_APPLICATION_PROPERTIES = "application.properties";
  
  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Properties properties = new Properties();
    try (InputStream input = MapperScannerConfig.class
        .getClassLoader()
        .getResourceAsStream(FILE_APPLICATION_PROPERTIES)) {
      if (input == null) {
        log.error("'{}' file not exist", FILE_APPLICATION_PROPERTIES);
        throw new Error();
      }
      properties.load(input);
    } catch (IOException ex) {
      log.error("'{}' load fail", FILE_APPLICATION_PROPERTIES);
    }
    String enabled = properties.getProperty("spring.sample.mybatis." + Constant.DBMS.VERTICA + ".enabled");
    return "true".equals(enabled);
  }
}