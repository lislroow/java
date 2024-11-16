package spring.sample.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;
import spring.sample.common.constant.Constant;
import spring.sample.config.properties.MybatisProperties;

@Slf4j
public enum DBMS_TYPE {
  
  H2(Constant.DBMS.H2),
  MARIA(Constant.DBMS.MARIA),
  ORACLE(Constant.DBMS.ORACLE),
  ;
  
  private String code;
  private Boolean primary;
  
  private DBMS_TYPE(String code) {
    this.code = code;
  }
  
  public String code() {
    return this.code;
  }
  
  public boolean isPrimary() {
    return Optional.ofNullable(primary).orElse(false);
  }
  
  public static void setPrimary(String code) {
    DBMS_TYPE type = DBMS_TYPE.fromCode(code);
    type.primary = true;
    log.warn("setting up mybatis primary: {}", type.code());
  }
  
  public String capital() {
    return this.code.substring(0, 1).toUpperCase() + this.code.substring(1);
  }
  
  public String sqlSessionFactoryBeanName() {
    return this.code + MybatisProperties.SQL_SESSION_FACTORY_BEAN;
  }
  
  public static DBMS_TYPE fromCode(String code) {
      return Arrays.stream(DBMS_TYPE.values())
          .filter(item -> StringUtils.equals(code, item.code()))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException(String.format("'%s' not exist.", code)));
  }
}