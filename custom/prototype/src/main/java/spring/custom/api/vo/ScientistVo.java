package spring.custom.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageRequest;

public class ScientistVo {
  
  private ScientistVo() { }
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class SearchResult extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
    private String fosNm;
  }
  
  @Data
  @Builder
  public static class SearchParam extends PageRequest {
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
  public static class AddScientist extends AuditVo {
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
  public static class ModifyScientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
}