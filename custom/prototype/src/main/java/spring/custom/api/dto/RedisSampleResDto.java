package spring.custom.api.dto;

import java.util.List;

import lombok.Data;

public class RedisSampleResDto {

  private RedisSampleResDto() { }
  
  @Data
  public static class SampleList {
    private List<RedisSample> list;
    
    public SampleList(List<RedisSample> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class RedisSample {
    private Integer id;
    private String name;
  }
  
}
