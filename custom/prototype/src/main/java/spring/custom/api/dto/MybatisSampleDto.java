package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.code.EnumScientist;
import spring.custom.common.mybatis.PageInfo;
import spring.custom.common.vo.AuditVo;

public class MybatisSampleDto {
  
  private MybatisSampleDto() { }
  
  // search scientist
  @Data
  public static class ScientistRes extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private EnumScientist.FieldOfStudy fosCd;
    private String fosNm;
    private List<ImageRes> images;
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
  
  
  // search scientist-image
  @Data
  public static class ScientistImageRes {
    private String id;
    private Integer scientistId;
    private Integer seq;
    private String format;
    private String imageDesc;
    private String imageDate;
    
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosNm;
  }
  
  @Data
  public static class ScientistImageListRes {
    private List<ScientistImageRes> list;
    
    public ScientistImageListRes(List<ScientistImageRes> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedScientistImageListRes {
    private PageInfo paged;
    private List<ScientistImageListRes> list;
  }
  
  
  // find scientist-image
  @Data
  public static class ImageRes {
    private String id;
    private Integer scientistId;
    private Integer seq;
    private String format;
    private String imageDesc;
    private String imageDate;
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
