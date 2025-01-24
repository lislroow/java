package spring.custom.common.enumcode;

import org.springframework.core.convert.converter.Converter;

import spring.custom.code.EnumScientist.FieldOfStudy;

/* fail code */ public class StringToFieldOfStudyConverter implements Converter<String, FieldOfStudy> {
  
  @Override
  public FieldOfStudy convert(String source) {
    for (FieldOfStudy field : FieldOfStudy.values()) {
      if (field.value.equals(source)) {
        return field;
      }
    }
    throw new IllegalArgumentException("Invalid value for FieldOfStudy: " + source);
  }
  
}
