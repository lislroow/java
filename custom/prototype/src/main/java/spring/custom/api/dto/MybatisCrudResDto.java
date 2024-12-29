package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.Paged;

@Data
public class MybatisCrudResDto {

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
