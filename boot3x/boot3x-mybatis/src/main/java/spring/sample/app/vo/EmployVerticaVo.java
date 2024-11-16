package spring.sample.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.sample.common.audit.AuditVerticaVo;
import spring.sample.common.mybatis.Pageable;

@Data
@EqualsAndHashCode(callSuper=false)
public class EmployVerticaVo extends AuditVerticaVo {
  
  private String id;
  private String name;
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class FindVo extends Pageable {
    private String id;
    private String name;
  }
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class AddVo extends AuditVerticaVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ModifyVo extends AuditVerticaVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class RemoveVo extends AuditVerticaVo {
    private String id;
    private String name;
  }

}
