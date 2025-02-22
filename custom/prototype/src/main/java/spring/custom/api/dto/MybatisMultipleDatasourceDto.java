package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.mybatis.PageInfo;
import spring.custom.common.vo.AuditVo;

public class MybatisMultipleDatasourceDto {
  
  @Data
  public static class ScientistRes extends AuditVo {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
    private String fosNm;
  }
  
  @Data
  public static class ScientistListRes {
    private List<ScientistRes> list;
  }
  
  @Data
  public static class PagedScientistListRes {
    private PageInfo paged;
    private List<ScientistRes> list;
  }
  
}
