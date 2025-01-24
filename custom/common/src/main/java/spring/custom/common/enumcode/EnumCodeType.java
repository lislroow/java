package spring.custom.common.enumcode;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EnumCodeType {
  
  @JsonValue
  String getValue();
  String getLabel();
  
  static <T extends EnumCodeType> T fromValue(Class<T> enumType, String value) {
    for (T enumConstant : enumType.getEnumConstants()) {
      if (enumConstant.getValue().equals(value)) {
        return enumConstant;
      }
    }
    return null;
  }

}
