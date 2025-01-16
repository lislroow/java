package spring.custom.api.vo;

import lombok.Data;

public class CommonCodeVo {
  
  private CommonCodeVo() {}
  
  @Data
  public static class CodeVo {
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
}
