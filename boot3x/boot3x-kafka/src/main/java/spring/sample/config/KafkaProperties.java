package spring.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;

//@Slf4j
//@Data
@ConfigurationProperties(prefix = KafkaProperties.PREFIX)
public class KafkaProperties implements Condition {
  
  public static final String PREFIX = "sample.kafka";
  
  private static Logger log = LoggerFactory.getLogger(KafkaProperties.class);
  
  private boolean enabled;
  
  private String bootstrapServers; 
  private String groupId; 
  private String autoOffsetReset;
  
  @Override 
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Environment env = context.getEnvironment();
    
    String keyBootstrapServers = "sample.kafka.bootstrap-servers";
    String keyGroupId = "sample.kafka.group-id";
    boolean isExistBootstrapServers = env.containsProperty(keyBootstrapServers);
    boolean isExistGroupId = env.containsProperty(keyGroupId);
    
    this.setEnabled(isExistBootstrapServers && isExistGroupId); 
    
    if (this.enabled) {
      log.debug("check for kafka enabled: key={}, exist={}", keyBootstrapServers, isExistBootstrapServers);
      log.debug("check for kafka enabled: key={}, exist={}", keyGroupId, isExistGroupId);
    } else {
      log.info("check for kafka enabled: key={}, exist={}", keyBootstrapServers, isExistBootstrapServers);
      log.info("check for kafka enabled: key={}, exist={}", keyGroupId, isExistGroupId);
    }
    return enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getAutoOffsetReset() {
    return autoOffsetReset;
  }

  public void setAutoOffsetReset(String autoOffsetReset) {
    this.autoOffsetReset = autoOffsetReset;
  }
}
