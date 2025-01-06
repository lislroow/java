package spring.custom.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.custom.common.audit.AuditVerticaVo;
import spring.custom.common.mybatis.PageRequest;

@Data
@EqualsAndHashCode(callSuper=false)
public class ScientistVerticaVo extends AuditVerticaVo {
  
  private Integer id;
  private String name;
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class FindVo extends PageRequest {
    private Integer id;
    private String name;
  }
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class AddVo extends AuditVerticaVo {
    private Integer id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ModifyVo extends AuditVerticaVo {
    private Integer id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class RemoveVo extends AuditVerticaVo {
    private Integer id;
    private String name;
  }

}
