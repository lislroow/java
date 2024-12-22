package spring.sample.app.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CardVo {

  private Long id;
  private String cardNo;
  private LocalDateTime issueDate;
  private String holderName;
  
}
