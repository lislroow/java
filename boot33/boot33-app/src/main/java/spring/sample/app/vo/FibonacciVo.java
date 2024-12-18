package spring.sample.app.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "fibonacci")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FibonacciVo {

  @Id
  private Integer days;
  private Long num;
  
}
