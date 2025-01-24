package spring.custom.code;

//import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spring.custom.common.enumcode.Code;
import spring.custom.common.enumcode.EnumCodeType;

public class EnumScientist {

  @Code("scientist:fos")
  @Getter
  @RequiredArgsConstructor
  public enum FieldOfStudy implements EnumCodeType {
    
    PHYSICS("P", "pyhsics"),
    CHEMISTRY("C", "chemistry"),
    MATHEMATICS("M", "mathematics"),
    BIOLOGY("B", "biology"),
    ASTRONOMY("A", "astronomy"),
    ;
    
    public final String value;
    public final String label;
    
    /* unused code */ //@JsonCreator
    //public static FieldOfStudy fromValue(String value) {
    //  for (FieldOfStudy field : FieldOfStudy.values()) {
    //    if (field.value.equals(value)) {
    //      return field;
    //    }
    //  }
    //  throw new IllegalArgumentException("Invalid value for FieldOfStudy: " + value);
    //}
  }
  
  @Code("scientist:physics")
  @Getter
  @RequiredArgsConstructor
  public enum Physics implements EnumCodeType {
    
    CLASSICAL("C", "Classical Mechanics"),
    ELECTROMAGNETIC("E", "Electromagnetic Mechanics"),
    QUANTUM("Q", "Quantum Mechanics"),
    FLUID("F", "Fluid Mechanics"),
    ;
    
    public final String value;
    public final String label;
  }
  
}
