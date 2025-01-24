package spring.custom.common.enumcode;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EnumCodeType {
  
  @JsonValue
  String getValue();
  String getLabel();
  
}
