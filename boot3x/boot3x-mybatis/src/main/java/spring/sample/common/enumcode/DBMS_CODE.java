package spring.sample.common.enumcode;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import spring.sample.common.constant.Constant;

public enum DBMS_CODE {
  
  H2(Constant.DBMS.H2),
  MARIA(Constant.DBMS.MARIA),
  ORACLE(Constant.DBMS.ORACLE),
  ;
  
  private String code;
  private DBMS_CODE(String code) {
    this.code = code;
  }

  public String code() {
    return this.code;
  }
  
  public String capital() {
    return this.code.substring(0, 1).toUpperCase() + this.code.substring(1);
  }
  
  public static DBMS_CODE fromCode(String code) {
      return Arrays.stream(DBMS_CODE.values())
          .filter(item -> StringUtils.equals(code, item.code()))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException(String.format("code '%s'는 존재하지 않습니다.", code)));
  }
}
