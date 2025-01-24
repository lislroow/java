package spring.custom.code;

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
