package spring.custom.api.dto;

import lombok.Data;

public class RedisSampleReqDto {
  
  private RedisSampleReqDto() { }
  
  @Data
  public static class SearchSample {
    private String name;
  }
  
  @Data
  public static class Add {
    private Integer id;
    private String name;
  }
  
}
