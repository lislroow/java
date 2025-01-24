package spring.custom.config;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.enumcode.EnumCode;
import spring.custom.common.enumcode.EnumCodeType;
import spring.custom.common.enumcode.EnumMapper;

@Configuration
@Slf4j
public class EnumMapperConfig {
  
  @Value("${custom.code-package:spring.custom.code}")
  private String[] codePackages;
  
  @Bean
  EnumMapper enumMapper() {
    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .forPackages(codePackages)
        .addScanners(Scanners.SubTypes));
    Set<Class<? extends EnumCodeType>> enumCodeSet = new HashSet<>();
    Set<Class<? extends EnumCodeType>> classes = reflections.getSubTypesOf(EnumCodeType.class);
    for (Class<? extends EnumCodeType> clazz : classes) {
      if (EnumCodeType.class.isAssignableFrom(clazz) && Modifier.isPublic(clazz.getModifiers())) {
        enumCodeSet.add(clazz);
      }
    }
    
    EnumMapper enumMapper = new EnumMapper(enumCodeSet);
    
    /* for debug */ if (log.isInfoEnabled()) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        log.info("allCodes: {}", objectMapper.writeValueAsString(enumMapper.allCodes()));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    return enumMapper;
  }
}
