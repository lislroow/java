package spring.custom.api.dto;

import lombok.Data;

public class MybatisSampleReqDto {
  
  private MybatisSampleReqDto() { }

  @Data
  public static class AddScientist {
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
  
  @Data
  public static class ModifyScientist {
    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String fosCd;
  }
}
