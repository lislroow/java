package spring.sample.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
@Table(name = "trace_api")
@EqualsAndHashCode(callSuper=false)
public class TraceApiEntity {
  
  /*
  @PrePersist
  public void prePersist() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }
  }
  */
  
  @Id
  @Column(columnDefinition = "varchar(36)")
  private String id;
  
  @Column(columnDefinition = "varchar(15)")
  private String remoteAddr;
  
  @Column(columnDefinition = "varchar(4000)")
  private String param;
  
  private Long expireTime;
  private Long accessTime;
  
  @Type(type = "yes_no")
  private Boolean failYn;
  
}
