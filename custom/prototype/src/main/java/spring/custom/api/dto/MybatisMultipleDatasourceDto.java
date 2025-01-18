package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageInfo;

public class MybatisMultipleDatasourceDto {

  public static class Scientist extends ScientistVo { }
  
  @Data
  public static class ScientistListRes {
    private List<Scientist> list;
  }
  
  @Data
  public static class PagedScientistListRes {
    private PageInfo paged;
    private List<Scientist> list;
  }
  
}
