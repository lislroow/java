package spring.sample.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CardStatsResDto {
  
  private CardStatsResDto () { }
  
  @Data
  public static class IssueInfo {
    private String cardNo;
    private LocalDateTime issueDate;
  }
  
  @Data
  public static class Payments {
    private List<PaymentDetail> payments; 
  }
  
  @Data
  public static class PaymentDetail {
    private String cardNo;
    private LocalDateTime paidTime;
    private Long paidAmount;
    private String storeName;
  }
  
}
