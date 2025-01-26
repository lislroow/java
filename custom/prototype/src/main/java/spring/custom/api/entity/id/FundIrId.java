package spring.custom.api.entity.id;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundIrId implements Serializable {
  
  private static final long serialVersionUID = -4837066269335829019L;
  
  private String basYmd;
  private String fundCd;
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FundIrId that = (FundIrId) o;
    return Objects.equals(basYmd, that.basYmd) && Objects.equals(fundCd, that.fundCd);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(basYmd, fundCd);
  }
  
}
