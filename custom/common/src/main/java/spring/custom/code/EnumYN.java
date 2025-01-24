package spring.custom.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spring.custom.common.enumcode.Code;
import spring.custom.common.enumcode.EnumCodeType;

@Code("YN")
@Getter
@RequiredArgsConstructor
public enum EnumYN implements EnumCodeType {
  
  Y("Y", "Y"),
  N("N", "N"),
  ;
  
  public final String value;
  public final String label;
  
}
