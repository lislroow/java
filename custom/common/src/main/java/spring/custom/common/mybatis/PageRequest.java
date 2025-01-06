package spring.custom.common.mybatis;

import lombok.Data;

@Data
public class PageRequest {
  
  private Integer page;
  private Integer size;
  
  public static PageRequest of(Integer page, Integer size) {
    PageRequest pageRequest = new PageRequest();
    pageRequest.setPage(page);
    pageRequest.setSize(size);
    return pageRequest;
  }
  
}
