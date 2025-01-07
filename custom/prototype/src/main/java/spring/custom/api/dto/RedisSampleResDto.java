package spring.custom.api.dto;

import java.util.List;

import lombok.Data;

public class RedisSampleResDto {

  private RedisSampleResDto() { }
  
  @Data
  public static class SampleList {
    private List<Sample> list;
  }
  
  @Data
  public static class Sample {
    private Integer id;
    private String name;
    private Integer age;
  }
  
}
