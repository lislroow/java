package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;

@Slf4j
public enum DBMS {
  
  H2(Constant.DBMS_TYPE.H2),
  MARIA(Constant.DBMS_TYPE.MARIA),
  ORACLE(Constant.DBMS_TYPE.ORACLE),
  VERTICA(Constant.DBMS_TYPE.VERTICA),
  POSTGRES(Constant.DBMS_TYPE.POSTGRES),
  ;
  
  private String code;
  private Boolean primary;
  
  private DBMS(String code) {
    this.code = code;
  }
  
  public String code() {
    return this.code;
  }
  
  public boolean isPrimary() {
    return Optional.ofNullable(primary).orElse(false);
  }
  
  public static void setPrimary(String code) {
    DBMS type = DBMS.fromCode(code);
    type.primary = true;
    log.warn("setting up mybatis primary: {}", type.code());
  }
  
  public String capital() {
    return this.code.substring(0, 1).toUpperCase() + this.code.substring(1);
  }
  
  public String sqlSessionFactoryBeanName() {
    return this.code + Constant.BEAN.SQL_SESSION_FACTORY_BEAN;
  }
  
  public static DBMS fromCode(String code) {
      return Arrays.stream(DBMS.values())
          .filter(item -> item.code().equals(code))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException(String.format("'%s' not exist.", code)));
  }
}
