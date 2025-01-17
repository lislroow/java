package spring.custom.common.audit;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditVo {

  private LocalDateTime createTime;
  private LocalDateTime modifyTime;
  private String createId;
  private String modifyId;
  
}
