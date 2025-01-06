package spring.custom.common.audit;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditVo {

  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  private Integer createId;
  private Integer modifyId;
  
}
