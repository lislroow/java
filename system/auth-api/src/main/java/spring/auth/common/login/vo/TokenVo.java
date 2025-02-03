package spring.auth.common.login.vo;

import java.time.LocalDate;

import lombok.Data;
import spring.custom.code.EnumYN;


public class TokenVo {
  
  private TokenVo() { }
  
  @Data
  public static class ClientToken {
    private String clientId;
    private String tokenKey;  // condition
    private String tokenValue;
    private EnumYN enableYn;
    private String clientIp;
    private LocalDate expDate;
  }
  
}
