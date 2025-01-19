package spring.custom.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CommonCodeDto {
  
  private CommonCodeDto() {}
  
  @Data
  @AllArgsConstructor
  public static class AllCodeRes {
    private String cdGrp;
    List<CodeRes> list;
  }
  
  @Data
  public static class CodeRes {
    private String cd;
    private Integer seq;
    private String cdNm;
  }
  
}
