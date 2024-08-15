package spring.sample.app.code;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SOAP_CLIENT_TYPE {
  
  SPRING_WEBSERVICE("10", "spring webservice 모듈"),
  HTTP_CLIENT("20", "spring webservice 모듈"),
  ;
  
  SOAP_CLIENT_TYPE(String value, String desc) {
    this.value = value;
    this.desc = desc;
  }
  
  private final String value;
  
  private final String desc;
  
  @Override
  public String toString() {
      return this.value;
  }
  
  @JsonValue
  public String getValue() {
      return this.value;
  }
  
  public String getDesc() {
      return this.desc;
  }
  
  public static SOAP_CLIENT_TYPE valueOf(String value, RuntimeException e) {
      return Arrays.stream(SOAP_CLIENT_TYPE.values())
          .filter(item -> StringUtils.equals(value, item.getValue()))
          .findFirst()
          .orElseThrow(() -> e);
  }
}
