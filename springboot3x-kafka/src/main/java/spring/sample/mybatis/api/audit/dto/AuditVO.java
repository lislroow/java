package spring.sample.mybatis.api.audit.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import spring.sample.Constant;

public class AuditVO {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATETIME_FORMAT)
  private LocalDateTime createDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATETIME_FORMAT)
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
