package spring.custom.api.dto;

import lombok.Data;

public class RedisSampleReqDto {
  
  private RedisSampleReqDto() { }
  
  @Data
  public static class AddRedis {
    private Integer id;
    private String name;
    private Integer age;
  }
  
}
