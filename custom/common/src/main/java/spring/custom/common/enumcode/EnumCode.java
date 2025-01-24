package spring.custom.common.enumcode;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EnumCode {
  
  private final String code;
  private final String value;
  private final Integer seq;
  
  public EnumCode(EnumCodeType enumCodeType, Integer seq) {
    this.code = enumCodeType.getCode();
    this.value = enumCodeType.getValue();
    this.seq = seq;
  }
}
