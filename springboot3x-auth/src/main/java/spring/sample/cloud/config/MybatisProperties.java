package spring.sample.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

@ConfigurationProperties(prefix = MybatisProperties.PREFIX)
public class MybatisProperties implements Condition {
  
  Logger log = LoggerFactory.getLogger(MybatisProperties.class);

  private String mapperLocation;
  private String configFile;
  private String typeAliasesPackage;
  
  public static final String PREFIX = "sample.jdbc.mybatis";
  private boolean enabled;
  
  @Override 
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Environment env = context.getEnvironment();
    
    String keyConfigFile = "sample.jdbc.mybatis.config-file";
    String keyMapperLocation = "sample.jdbc.mybatis.mapper-location";
    boolean isExistConfigFile = env.containsProperty(keyConfigFile);
    boolean isExistMapperLocation = env.containsProperty(keyMapperLocation);
    
    this.setEnabled(isExistConfigFile && isExistMapperLocation);
    
    if (this.enabled) {
      log.debug("check for mybatis enabled: key={}, exist={}", keyConfigFile, isExistConfigFile);
      log.debug("check for mybatis enabled: key={}, exist={}", keyMapperLocation, isExistMapperLocation);
    } else {
      log.info("check for mybatis enabled: key={}, exist={}", keyConfigFile, isExistConfigFile);
      log.info("check for mybatis enabled: key={}, exist={}", keyMapperLocation, isExistMapperLocation);
    }
    return this.enabled;
  }
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  public String getMapperLocation() {
    return mapperLocation;
  }
  
  public void setMapperLocation(String mapperLocation) {
    this.mapperLocation = mapperLocation;
  }

  public String getConfigFile() {
    return configFile;
  }

  public void setConfigFile(String configFile) {
    this.configFile = configFile;
  }
  
  public String getTypeAliasesPackage() {
    return typeAliasesPackage;
  }

  public void setTypeAliasesPackage(String typeAliasesPackage) {
    this.typeAliasesPackage = typeAliasesPackage;
  }
  
}
