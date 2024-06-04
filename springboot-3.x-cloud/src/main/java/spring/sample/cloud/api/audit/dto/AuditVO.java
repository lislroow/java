package spring.sample.cloud.api.audit.dto;

import java.time.LocalDateTime;

public class AuditVO {

  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  private String createId;
  private String modifyId;
  
  public LocalDateTime getCreateDate() {
    return createDate;
  }
  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }
  public LocalDateTime getModifyDate() {
    return modifyDate;
  }
  public void setModifyDate(LocalDateTime modifyDate) {
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
