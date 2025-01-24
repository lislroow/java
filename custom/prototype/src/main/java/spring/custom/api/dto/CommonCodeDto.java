package spring.custom.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class CodeRes {
    private String cd;
    private String cdNm;
    private Integer seq;
  }
  
}
