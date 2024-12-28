package spring.component.app.dto;

import java.util.List;

import lombok.Data;
import spring.component.app.vo.ScientistVo;
import spring.component.common.mybatis.Paged;

@Data
public class MybatisMultipleDatasourceResDto {

  public static class Scientist extends ScientistVo { }
  
  @Data
  public static class ScientistList {
    private List<Scientist> list;
  }
  
  @Data
  public static class PagedScientistList {
    private Paged paged;
    private List<Scientist> list;
  }
  
}
