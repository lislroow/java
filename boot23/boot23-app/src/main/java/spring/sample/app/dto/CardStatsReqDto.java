package spring.sample.app.dto;

import lombok.Data;

@Data
public class CardStatsReqDto {
  
  @Data
  public static class Issue {
    private String cardNo;
  }
  
  @Data
  public static class Payment {
    private String cardNo;
  }
  
}
