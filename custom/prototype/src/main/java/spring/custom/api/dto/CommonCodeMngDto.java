package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.mybatis.PageInfo;

public class CommonCodeMngDto {
  
  private CommonCodeMngDto() {}
  
  @Data
  public static class CodeGroupRes {
    private String cdGrp;
    private String cdGrpNm;
    private String useYn;
  }
  
  @Data
  public static class CodeRes {
    private String cdGrp;
    private String cdGrpNm;
    private String cd;
    private Integer seq;
    private String cdNm;
    private String useYn;
  }
  
  @Data
  public static class PagedCodeGroupListRes {
    private PageInfo paged;
    private List<CodeGroupRes> list;
  }
  
}
