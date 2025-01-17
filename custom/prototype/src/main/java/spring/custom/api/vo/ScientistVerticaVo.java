package spring.custom.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.audit.AuditVerticaVo;
import spring.custom.common.mybatis.PageRequest;

@Data
public class ScientistVerticaVo extends AuditVerticaVo {
  
  private Integer id;
  private String name;
  
  @Data
  @Builder
  public static class SearchVo extends PageRequest {
    private Integer id;
    private String name;
  }
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class AddVo extends AuditVerticaVo {
    private Integer id;
    private String name;
  }
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ModifyVo extends AuditVerticaVo {
    private Integer id;
    private String name;
  }

}
