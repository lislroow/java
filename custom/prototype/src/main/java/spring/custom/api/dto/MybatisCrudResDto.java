package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.mybatis.PageInfo;

@Data
public class MybatisCrudResDto {
  
  @Data
  public static class Scientist {
    private Integer id;
    private String name;
  }
  
  @Data
  public static class ScientistList {
    private List<Scientist> list;
  }
  
  @Data
  public static class PagedScientistList {
    private PageInfo paged;
    private List<Scientist> list;
  }
  
}
