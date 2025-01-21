package spring.custom.api.vo;

import lombok.Builder;
import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageRequest;

public class ScientistVo {
  
  private ScientistVo() { }
  
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
  public static class ResultScientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
    private String fosNm;
  }
  
  @Data
  public static class AddScientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
  
  @Data
  public static class ModifyScientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
}