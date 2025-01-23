package spring.custom.api.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AuditEntity {

  @CreationTimestamp
  @Column(name = "create_time", columnDefinition = "timestamp DEFAULT now() NULL ")
  private LocalDateTime createTime;
  
  @CreationTimestamp
  @Column(name = "modify_time", columnDefinition = "timestamp DEFAULT now() NULL ")
  private LocalDateTime modifyTime;
  
}
