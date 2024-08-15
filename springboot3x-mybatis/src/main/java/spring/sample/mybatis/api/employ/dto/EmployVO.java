package spring.sample.mybatis.api.employ.dto;

import spring.sample.mybatis.api.audit.dto.AuditVO;

public class EmployVO extends AuditVO {

  private String id;
  private String name;
  
  // lombok 미적용 프로젝트에서 sample 데이터 생성 목적으로 생성
  public EmployVO() { }
  public EmployVO(String id, String name) { this.id = id; this.name = name; }
  // --
  
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
