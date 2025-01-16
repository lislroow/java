package spring.custom.api.dto;

import java.util.List;

import lombok.Data;

public class RedisSampleResDto {

  private RedisSampleResDto() { }
  
  @Data
  public static class RedisList {
    private List<Redis> list;
  }
  
  @Data
  public static class Redis {
    private Integer id;
    private String name;
    private Integer age;
  }
  
}
