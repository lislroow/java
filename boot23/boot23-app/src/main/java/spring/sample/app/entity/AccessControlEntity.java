package spring.sample.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@ToString
@Table(name = "access_control")
@EqualsAndHashCode(callSuper=false)
public class AccessControlEntity {
  
  @Id
  @Column(columnDefinition = "varchar(36)")
  private String id;
  
  @Column(columnDefinition = "varchar(15)")
  private String ipAddr;
  
  private Long delayTime;
  
}
