package spring.custom.common.mybatis;

import lombok.Data;

@Data
public class Paged {
  // param
  private Integer page;
  private Integer size;
  
  // result
  private Integer start;
  private Integer end;
  private Integer total = -1;
}