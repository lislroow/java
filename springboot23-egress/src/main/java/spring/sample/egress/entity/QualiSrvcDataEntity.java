package spring.sample.egress.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.sample.code.CD_QV_DATA_TYPE;
import spring.sample.code.CD_REQ_RES;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "srvc")
@EqualsAndHashCode(callSuper=false, exclude = "srvc")
@Table(name = "qv_service_data")
public class QualiSrvcDataEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  
  @Column(columnDefinition = "varchar(1000)")
  private String dataNameKo;
  
  @Column(columnDefinition = "varchar(1000)")
  private String dataNameEn;
  
  @Column(columnDefinition = "char(1)")
  private CD_REQ_RES reqRes;
  
  @Column(columnDefinition = "char(1)")
  private CD_QV_DATA_TYPE dataType;
  
  @ManyToOne(fetch = FetchType.LAZY)
  //@JoinColumn(name = "qv_service_id")
  @JoinColumn(name = "qv_service_id", referencedColumnName = "id")
  private QualiSrvcEntity srvc;
}
