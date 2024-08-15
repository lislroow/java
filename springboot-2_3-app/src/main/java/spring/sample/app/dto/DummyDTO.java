package spring.sample.app.dto;

import lombok.Builder;
import lombok.Data;

public class DummyDTO {

  @Data
  @Builder
  public static class DummyVO {
    private String id;
    private String name;
  }
  
}
