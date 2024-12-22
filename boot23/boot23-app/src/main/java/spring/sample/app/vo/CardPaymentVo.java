package spring.sample.app.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CardPaymentVo {

  private Long id;
  private String cardNo;
  private LocalDateTime paidDate;
  private Long paidAmount;
  private String storeName;
  
}
