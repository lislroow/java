package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageInfo;

@Data
public class MybatisMultipleDatasourceResDto {

  public static class Scientist extends ScientistVo { }
  
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
