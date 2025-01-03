package spring.market.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.market.api.dto.MyInfoReqDto;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  
  @Id
  @Column(columnDefinition = "varchar(14)")
  private String id;
  
  @Column(columnDefinition = "varchar(50)")
  private String name;
  
  @Column(columnDefinition = "varchar(255)")
  private String email;
  
  @Column(columnDefinition = "datetime(6)")
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime createDate;
  
  @Column(columnDefinition = "datetime(6)", nullable = false)
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime modifyDate;
  
  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<CustomerDelivery> deliveries;
  
  public Customer saveBasicInfo(MyInfoReqDto.UserReq request) {
    this.name = request.getName();
    this.setModifyDate(LocalDateTime.now());
    return this;
  }
  
  public Customer saveDeliveries() {
    return this;
  }
}
