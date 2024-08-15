package spring.sample.mybatis.api.audit.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AuditVO {

//  private LocalDateTime createDate;
//  private LocalDateTime modifyDate;
  private Timestamp createDate;
  private Timestamp modifyDate;
  private String createId;
  private String modifyId;
  
//  public LocalDateTime getCreateDate() {
//    return createDate;
//  }
//  public void setCreateDate(LocalDateTime createDate) {
//    this.createDate = createDate;
//  }
//  public LocalDateTime getModifyDate() {
//    return modifyDate;
//  }
//  public void setModifyDate(LocalDateTime modifyDate) {
//    this.modifyDate = modifyDate;
//  }
  public Timestamp getCreateDate() {
    return createDate;
  }
  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }
  public Timestamp getModifyDate() {
    return modifyDate;
  }
  public void setModifyDate(Timestamp modifyDate) {
    this.modifyDate = modifyDate;
  }
  public String getCreateId() {
    return createId;
  }
  public void setCreateId(String createId) {
    this.createId = createId;
  }
  public String getModifyId() {
    return modifyId;
  }
  public void setModifyId(String modifyId) {
    this.modifyId = modifyId;
  }
}
