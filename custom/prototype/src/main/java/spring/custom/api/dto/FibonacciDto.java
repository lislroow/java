package spring.custom.api.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FibonacciDto {
  
  private FibonacciDto() {}
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ResultRes {
    private List<Map<String, String>> list;
    private List<String> times;
    private String summary;
  }
  
}
