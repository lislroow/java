package spring.auth.api.vo;

import java.time.LocalDate;

import lombok.Data;
import spring.custom.common.enumcode.YN;


public class ClientTokenVo {
  
  private ClientTokenVo() { }
  
  @Data
  public static class VerifyToken {
    private String clientId;
    private String tokenKey;  // condition
    private String tokenValue;
    private YN enableYn;
    private String clientIp;
    private LocalDate expDate;
  }
  
}
