package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.mybatis.PageInfo;

public class CommonCodeMngResDto {
  
  private CommonCodeMngResDto() {}
  
  @Data
  public static class CodeGroup {
    private String cdGrp;
    private String cdGrpNm;
    private String useYn;
  }
  
  @Data
  public static class Code {
    private String cdGrp;
    private String cdGrpNm;
    private String cd;
    private Integer seq;
    private String cdNm;
    private String useYn;
  }
  
  @Data
  public static class PagedCodeGroupList {
    private PageInfo paged;
    private List<CodeGroup> list;
  }
  
}
