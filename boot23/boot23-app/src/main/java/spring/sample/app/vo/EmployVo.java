package spring.sample.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.sample.common.audit.AuditVo;
import spring.sample.common.mybatis.Pageable;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class EmployVo extends AuditVo {
  
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
  public static class AddVo extends AuditVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ModifyVo extends AuditVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class RemoveVo extends AuditVo {
    private String id;
    private String name;
  }
}