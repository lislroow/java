package spring.custom.api.dto;

import lombok.Data;

public class CommonCodeDto {
  
  private CommonCodeDto() {}
  
  @Data
  public static class CodeRes {
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
}
