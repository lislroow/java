package spring.custom.common.enumcode;

import org.springframework.core.convert.converter.Converter;

/* dead code */ public class StringToEnumCodeTypeConverter<T extends EnumCodeType> implements Converter<String, T> {

  private final Class<T> enumType;

  public StringToEnumCodeTypeConverter(Class<T> enumType) {
    this.enumType = enumType;
  }

  @Override
  public T convert(String source) {
    for (T constant : enumType.getEnumConstants()) {
      if (constant.getValue().equals(source)) {
        return constant;
      }
    }
    throw new IllegalArgumentException("Invalid value: " + source);
  }
  
}