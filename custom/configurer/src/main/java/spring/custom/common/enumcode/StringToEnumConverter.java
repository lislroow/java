package spring.custom.common.enumcode;

import org.springframework.core.convert.converter.Converter;

/* dead code */ public class StringToEnumConverter<T extends Enum<T> & EnumCodeType> implements Converter<String, Enum<T>> {

  private final Class<T> enumType;
  
  public StringToEnumConverter(Class<T> enumType) {
    this.enumType = enumType;
  }
  
  @Override
  public T convert(String source) {
    for (T constant : enumType.getEnumConstants()) {
      if (constant.getValue().equals(source)) {
        return constant;
      }
    }
    throw new IllegalArgumentException("Invalid value for " + enumType.getSimpleName() + ": " + source);
  }
  
}
