package spring.sample.mybatis.api.employ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.sample.mybatis.config.mybatis.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployPageREQ extends Pageable {
  
  private String id;
  private String name;
}
