package spring.sample.webmvc.config.mybatis;

public class Pageable {
  
  protected Integer page;
  protected Integer pageSize;
  
  public Integer getPage() {
    return page;
  }
  public void setPage(Integer page) {
    this.page = page;
  }
  public Integer getPageSize() {
    return pageSize;
  }
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}