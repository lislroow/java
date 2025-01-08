package spring.custom.common.audit;

import lombok.Data;

@Data
public class AuditVerticaVo {

  private String createDate;
  private String modifyDate;
  private Integer createId;
  private Integer modifyId;

}
