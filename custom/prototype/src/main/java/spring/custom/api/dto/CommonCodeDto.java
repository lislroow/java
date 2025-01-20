package spring.custom.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommonCodeDto {
  
  private CommonCodeDto() {}
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
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
