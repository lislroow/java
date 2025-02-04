package spring.custom.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public class AuditEntity {

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createTime;
  
  @UpdateTimestamp
  private LocalDateTime modifyTime;
  
  @CreatedBy
  @Column(length = 16, updatable = true)
  private String createId;
  
  @LastModifiedBy
  @Column(length = 16, nullable = true)
  private String modifyId;
  
}
