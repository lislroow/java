package spring.custom.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spring.custom.common.enumcode.Code;
import spring.custom.common.enumcode.EnumCodeType;

public class EnumSearchMode {

  @Code("search-mode:numeric")
  @Getter
  @RequiredArgsConstructor
  public enum Numeric implements EnumCodeType {
    
    EQ("eq", "equal"),
    GT("gt", "greaterThan"),
    GE("ge", "greaterThanOrEqual"),
    LT("lt", "lessThan"),
    LE("le", "lessThanOrEqual"),
    ;
    
    public final String value;
    public final String label;
  }
  
  @Code("search-mode:numeric")
  @Getter
  @RequiredArgsConstructor
  public enum Char implements EnumCodeType {
    
    EQ("eq", "equal"),
    GT("like", "like"),
    GE("sw", "startsWith"),
    LT("ew", "endsWith"),
    ;
    
    public final String value;
    public final String label;
  }
  
}
