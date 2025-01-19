package spring.custom.api.vo;

import lombok.Data;

public class CommonCodeVo {
  
  private CommonCodeVo() {}
  
  @Data
  public static class AllCodeVo {
    private String cdGrp;
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
  @Data
  public static class CodeVo {
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
}
