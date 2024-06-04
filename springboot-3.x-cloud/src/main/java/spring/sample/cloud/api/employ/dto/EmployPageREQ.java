package spring.sample.cloud.api.employ.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.sample.cloud.config.mybatis.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployPageREQ extends Pageable {

  @NotEmpty(message = "id 가 '@NotEmpty' 입니다.")
  private String id;
  
  @NotBlank(message = "name 이 '@NotBlank' 입니다.")
  private String name;
}
