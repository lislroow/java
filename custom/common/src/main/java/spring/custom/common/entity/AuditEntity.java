package spring.custom.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AuditEntity {

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createTime;
  
  @UpdateTimestamp
  private LocalDateTime modifyTime;
  
}
