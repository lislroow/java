package spring.custom.api.entity;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.custom.api.entity.id.FundIrId;
import spring.custom.common.entity.AuditEntity;

@Entity
@Table(name = "fn_ir_d")
@IdClass(FundIrId.class)
@SQLDelete(sql = "UPDATE fn_ir_d SET deleted = true WHERE id = ?")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundIrEntity extends AuditEntity {
  
  @Column(insertable = false)
  private Boolean deleted;
  
  @Id
  private String basYmd;
  
  @Id
  private String fundCd;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fundCd", insertable = false, updatable = false)
  private FundMstEntity fundMst;
  
  private Double fundSizeAmt;
  private Double fundUnitAmt;
  private Double basPrice;
  private Double taxBasPrice;
  private Double ir;
  private Double bmIr;
  private Double actIr;
  private Double irIdx;
  private Double bmIrIdx;
  private Double actIrIdx;

}
