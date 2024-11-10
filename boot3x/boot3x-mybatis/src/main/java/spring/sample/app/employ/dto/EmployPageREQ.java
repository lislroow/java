package spring.sample.app.employ.dto;

import spring.sample.config.mybatis.Pageable;

public class EmployPageREQ extends Pageable {
  
  private String id;
  private String name;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
