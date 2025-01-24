package spring.custom.common.enumcode;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EnumCode {
  
  private final String value;
  private final String label;
  private final Integer seq;
  
  public EnumCode(EnumCodeType enumCodeType, Integer seq) {
    this.value = enumCodeType.getValue();
    this.label = enumCodeType.getLabel();
    this.seq = seq;
  }
}
