package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.mybatis.PageInfo;

public class MybatisSampleResDto {
  
  private MybatisSampleResDto() { }
  
  @Data
  public static class Scientist {
    private Integer id;
    private String name;
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
