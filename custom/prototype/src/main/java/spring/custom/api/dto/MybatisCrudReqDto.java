package spring.custom.api.dto;

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
    private Integer id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class ModifyDto extends AuditVo {
    private Integer id;
    private String name;
  }
}
