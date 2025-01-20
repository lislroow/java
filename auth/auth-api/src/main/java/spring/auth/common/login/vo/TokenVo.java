package spring.auth.common.login.vo;

import java.time.LocalDate;

import lombok.Data;
import spring.custom.common.enumcode.YN;


public class TokenVo {
  
  private TokenVo() { }
  
  @Data
  public static class ClientToken {
    private String clientId;
    private String tokenKey;  // condition
    private String tokenValue;
    private YN enableYn;
    private String clientIp;
    private LocalDate expDate;
  }
  
}
