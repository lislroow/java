package spring.sample.app.dto;

import java.util.List;

import lombok.Data;
import spring.sample.app.vo.EmployVo;
import spring.sample.common.mybatis.Paged;

@Data
public class EmployResDto {

  public static class Employ extends EmployVo { }
  
  @Data
  public static class EmployList {
    private List<Employ> list;
  }
  
  @Data
  public static class PagedEmployList {
    private Paged paged;
    private List<Employ> list;
  }
  
}
