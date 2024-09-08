package spring.sample.egress.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "datas")
@EqualsAndHashCode(callSuper=false, exclude = "datas")
@Table(name = "qv_service")
public class QualiSrvcEntity {
  
  @Id
  @Column(columnDefinition = "varchar(4)")
  private String id;
  
  @Column(columnDefinition = "varchar(500)")
  private String srvcName;
  
  @OneToMany(mappedBy = "srvc", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<QualiSrvcDataEntity> datas = new ArrayList<QualiSrvcDataEntity>();
}
