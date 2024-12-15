package spring.sample.app.dto;

import java.util.List;

import lombok.Data;
import spring.sample.app.vo.ScientistVo;
import spring.sample.common.mybatis.Paged;

@Data
public class ScientistResDto {

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