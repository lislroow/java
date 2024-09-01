package spring.sample.common.aspectj.entity;

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
@Table(name = "ns_blocked_client")
@EqualsAndHashCode(callSuper=false)
public class NsBlockedClientEntity {
  
  @Id
  @Column(columnDefinition = "varchar(36)")
  private String id;
  
  //@Column(columnDefinition = "varchar(28)")
  @Column(columnDefinition = "varchar(15)")
  private String remoteAddr;
  
  private Long unblockTime;
}
