package spring.custom.initial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;
import spring.custom.common.util.AsciiTable;

@Component
@ConditionalOnProperty(prefix = "custom.application-ready-handler", name = Constant.DISABLED, havingValue = "false", matchIfMissing = true)
@Slf4j
public class ApplicationReadyHandler {
  
  public static final List<String> CORE_LIST = Arrays.asList(
      "spring-boot",
      "spring-cloud-starter",
      "mybatis",
      "lombok-mapstruct-binding",
      "mapstruct",
      "modelmapper",
      "jasypt-spring-boot-starter",
      "log4jdbc-log4j2-jdbc4.1"
      );
  
  public static final List<String> JDBC_LIST = Arrays.asList(
      "h2",
      "mariadb-java-client",
      "ojdbc8",
      "vertica-jdbc",
      "postgresql"
      );
  
  @EventListener
  public void applicationReady(ApplicationReadyEvent event) {
    List<Classpath.BootJarVo> list = Classpath.getBootJars();
    List<Classpath.BootJarVo> coreList = list.stream()
        .filter(item -> CORE_LIST.contains(item.getJar()))
        .collect(Collectors.toList());
    log.info("Application Info # Core Libraries\n{}", AsciiTable.getTable(coreList, "jar", "ver"));
    
    List<Classpath.BootJarVo> jdbcList = list.stream()
        .filter(item -> JDBC_LIST.contains(item.getJar()))
        .collect(Collectors.toList());
    log.info("Application Info # JDBC Libraries\n{}", AsciiTable.getTable(jdbcList, "jar", "ver"));
  }
}
