package spring.sample.egress.entity.id;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class QualiSrvcDataId {

  @Column(columnDefinition = "varchar(20)")
  private String srvcId;
  
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(columnDefinition = "int(2)")
  private Integer srvcDataId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    QualiSrvcDataId that = (QualiSrvcDataId) o;
    return Objects.equals(srvcId, that.srvcId) &&
        Objects.equals(srvcDataId, that.srvcDataId);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(srvcId, srvcDataId);
  }
}
