package spring.custom.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.custom.common.entity.AuditEntity;

@Entity
@Table(name = "pt_star")
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarEntity extends AuditEntity {
  
  @Column(insertable = false)
  private Boolean deleted;
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pt_star_seq")
  @SequenceGenerator(
    name = "pt_star_seq",
    sequenceName = "pt_star_id_seq",
    allocationSize = 1
  )
  private Integer id;
  private String name;
  private Double distance;
  private Double brightness;
  private Double mass;
  private Integer temperature;
  
}
