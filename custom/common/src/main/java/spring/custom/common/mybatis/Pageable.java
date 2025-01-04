package spring.custom.common.mybatis;

import lombok.Data;

@Data
public class Pageable {
  
  protected Integer page;
  protected Integer size;
  
  public void setPageable(Pageable pageable) {
    this.page = pageable.page == null ? 1 : pageable.page;
    this.size = pageable.size == null ? 10 : pageable.size;
  }
  
  public static Pageable of(Integer page, Integer size) {
    Pageable pageable = new Pageable();
    pageable.setPage(page);
    pageable.setSize(size);
    return pageable;
  }
}