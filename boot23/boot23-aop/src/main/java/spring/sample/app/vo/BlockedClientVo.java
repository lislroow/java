package spring.sample.app.vo;

import lombok.Data;

@Data
public class BlockedClientVo {
  
  private String remoteAddr;
  private Long unblockTime;
  
}
