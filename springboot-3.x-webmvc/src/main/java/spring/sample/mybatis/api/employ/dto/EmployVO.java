package spring.sample.mybatis.api.employ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.sample.mybatis.api.audit.dto.AuditVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployVO extends AuditVO {

  private String id;
  private String name;
  
}
