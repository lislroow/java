package spring.custom.api.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.custom.common.entity.AuditEntity;

@Entity
@Table(name = "fn_fund_m")
@SQLDelete(sql = "UPDATE fn_fund_m SET deleted = true WHERE id = ?")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundMstEntity extends AuditEntity {
  
  @Column(insertable = false)
  private String deleted;
  
  @Id
  private String fundCd;
  
  private String amcCd;
  private String amcFundCd;
  private String fundFnm;
  private String fundGb;
  private String kitcaSortCd;
  private String xtypeLcd;
  private String xtypeCd;
  private Integer unitPrice;
  private Integer basUnit;
  private String seoljYmd;
  private String chgYmd;
  private String haejiGb;
  private String haejiYmd;
  private BigDecimal fstSeoljAek;
  private BigDecimal fstSeoljJwa;
  private BigDecimal fstGijunGa;
  private Double stockPnibRt;
  private Double kosdaqPnibRt;
  private String bmCd;
  private String bmNm;
  private String bmEnm;
  private String excHdgeYnCd;
  private String kitcaTypeCd;
  
}
