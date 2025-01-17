package spring.custom.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScientistVo extends AuditVo {
  
  private Integer id;
  private String name;
  private Integer birthYear;
  private Integer deathYear;
  private String fosCd;
  private String fosNm;
  
  @Data
  @Builder
  public static class SearchVo extends PageRequest {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class AddVo extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ModifyVo extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
}