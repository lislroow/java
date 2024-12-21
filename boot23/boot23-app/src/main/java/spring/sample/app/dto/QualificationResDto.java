package spring.sample.app.dto;

import lombok.Data;

@Data
public class QualificationResDto<T> {

  @Data
  public static class SoapRes {
    private Boolean qualified;
  }
  
}
