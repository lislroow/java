package spring.custom.api.dto;

import lombok.Data;

public class CommonCodeResDto {
  
  private CommonCodeResDto() {}
  
  @Data
  public static class Code {
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
}
