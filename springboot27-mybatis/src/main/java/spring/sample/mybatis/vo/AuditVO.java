package spring.sample.mybatis.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditVO {

  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  private String createId;
  private String modifyId;
  
}
