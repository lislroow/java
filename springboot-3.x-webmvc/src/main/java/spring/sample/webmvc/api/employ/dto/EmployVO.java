package spring.sample.webmvc.api.employ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.sample.webmvc.api.audit.dto.AuditVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployVO extends AuditVO {

  private String id;
  private String name;
  
}
