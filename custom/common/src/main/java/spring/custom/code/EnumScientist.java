package spring.custom.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spring.custom.common.enumcode.Code;
import spring.custom.common.enumcode.EnumCodeType;

public class EnumScientist {

  @Code("FOS")
  @Getter
  @RequiredArgsConstructor
  public enum FieldOfStudy implements EnumCodeType {
    
    PHYSICS("P", "pyhsics"),
    CHEMISTRY("C", "chemistry"),
    MATHEMATICS("M", "mathematics"),
    BIOLOGY("B", "biology"),
    ASTRONOMY("A", "astronomy"),
    ;
    
    public final String code;
    public final String value;
  }

}
