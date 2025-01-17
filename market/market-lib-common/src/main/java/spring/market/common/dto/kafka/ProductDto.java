package spring.market.common.dto.kafka;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  private Integer id;
  private String name;
  private LocalDateTime createTime;
  private LocalDateTime modifyTime;
  
}
