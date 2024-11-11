package spring.sample.app.enumcode;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SOAP_CLIENT_TYPE {
  
  SPRING_WEBSERVICE("10", "spring webservice 모듈"),
  HTTP_CLIENT("20", "spring webservice 모듈"),
  ;
  
  SOAP_CLIENT_TYPE(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
  
  private final String code;
  
  private final String desc;
  
  @Override
  public String toString() {
      return this.code;
  }
  
  @JsonValue
  public String getCode() {
      return this.code;
  }
  
  public String getDesc() {
      return this.desc;
  }
  
  public static SOAP_CLIENT_TYPE fromCode(String code) {
      return Arrays.stream(SOAP_CLIENT_TYPE.values())
          .filter(item -> StringUtils.equals(code, item.getCode()))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException(String.format("code '%s'는 존재하지 않습니다.", code)));
  }
}
