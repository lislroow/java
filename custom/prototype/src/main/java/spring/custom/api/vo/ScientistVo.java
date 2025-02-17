package spring.custom.api.vo;

import lombok.Builder;
import lombok.Data;
import spring.custom.code.EnumScientist;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.vo.AuditVo;

public class ScientistVo {
  
  private ScientistVo() { }
  
  @Data
  @Builder
  public static class SearchParam extends PageRequest {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
    private Integer century;
  }
  
  @Data
  @Builder
  public static class SearchImageParam extends PageRequest {
    private String imageDesc;
    private String imageDate;
    
    private Integer scientistId;
    private String name;
    private EnumScientist.FieldOfStudy fosCd;
    private Integer century;
  }
  
  @Data
  public static class ResultScientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
    private String fosNm;
  }
  
  @Data
  public static class ResultImage extends AuditVo {
    private String id;
    private Integer scientistId;
    private Integer seq;
    private String imageDesc;
    private String imageDate;
  }
  
  @Data
  public static class ResultScientistImage {
    private String id;
    private Integer scientistId;
    private Integer seq;
    private String imageDesc;
    private String imageDate;
    
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosNm;
  }
  
  @Data
  public static class AddScientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
  }
  
  @Data
  public static class ModifyScientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
  }
}