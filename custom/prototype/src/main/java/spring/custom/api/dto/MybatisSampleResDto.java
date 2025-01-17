package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageInfo;

public class MybatisSampleResDto {
  
  private MybatisSampleResDto() { }
  
  @Data
  public static class Scientist extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
    private String fosNm;
  }
  
  @Data
  public static class ScientistList {
    private List<Scientist> list;
    
    public ScientistList(List<Scientist> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedScientistList {
    private PageInfo paged;
    private List<Scientist> list;
  }
  
}
