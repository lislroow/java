package spring.custom.api.dto;

import lombok.Data;

public class CommonCodeDto {
  
  private CommonCodeDto() {}
  
  @Data
  public static class AllCodeRes {
    private String cdGrp;
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
  @Data
  public static class FindCodeRes {
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
}
