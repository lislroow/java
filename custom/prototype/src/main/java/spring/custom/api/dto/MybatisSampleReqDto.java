package spring.custom.api.dto;

import lombok.Data;

public class MybatisSampleReqDto {
  
  private MybatisSampleReqDto() { }

  @Data
  public static class AddDto {
    private String name;
  }
  
  @Data
  public static class ModifyDto {
    private Integer id;
    private String name;
  }
}
