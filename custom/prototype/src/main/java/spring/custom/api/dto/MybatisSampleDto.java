package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.code.EnumScientist;
import spring.custom.common.mybatis.PageInfo;
import spring.custom.common.vo.AuditVo;

public class MybatisSampleDto {
  
  private MybatisSampleDto() { }
  
  @Data
  public static class ScientistRes extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
    private String fosNm;
    private List<ScientistImageRes> images;
  }
  
  @Data
  public static class ScientistImageRes {
    private String id;
    private Integer scientistId;
    private Integer seq;
    private String imageDesc;
    private String imageDate;
  }
  
  @Data
  public static class ScientistListRes {
    private List<ScientistRes> list;
    
    public ScientistListRes(List<ScientistRes> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedScientistListRes {
    private PageInfo paged;
    private List<ScientistRes> list;
  }
  
  @Data
  public static class AddScientistReq {
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
  }
  
  @Data
  public static class ModifyScientistReq {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
  }
  
}
