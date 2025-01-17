package spring.custom.common.audit;

import lombok.Data;

@Data
public class AuditVerticaVo {

  private String createTime;
  private String modifyTime;
  private String createId;
  private String modifyId;

}
