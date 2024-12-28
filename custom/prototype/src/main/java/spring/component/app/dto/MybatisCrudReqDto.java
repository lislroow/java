package spring.component.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import spring.custom.common.audit.AuditVo;

@Data
public class MybatisCrudReqDto {

  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class AddDto extends AuditVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class ModifyDto extends AuditVo {
    private String id;
    private String name;
  }
}
