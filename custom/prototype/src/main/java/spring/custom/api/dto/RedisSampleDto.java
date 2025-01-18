package spring.custom.api.dto;

import java.util.List;

import lombok.Data;

public class RedisSampleDto {
  
  private RedisSampleDto() { }
  
  @Data
  public static class RedisRes {
    private Integer id;
    private String name;
    private Integer age;
  }
  
  @Data
  public static class RedisListRes {
    private List<RedisRes> list;
  }
  
  @Data
  public static class RedisAddReq {
    private Integer id;
    private String name;
    private Integer age;
  }
  
  
}
