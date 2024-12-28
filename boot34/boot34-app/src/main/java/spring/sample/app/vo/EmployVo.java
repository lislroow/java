package spring.sample.app.vo;

import spring.custom.common.audit.AuditVo;

public class EmployVo extends AuditVo {

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
