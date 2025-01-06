package spring.sample.app.vo;

import spring.custom.common.audit.AuditVo;

public class EmployVo extends AuditVo {

  private Integer id;
  private String name;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
