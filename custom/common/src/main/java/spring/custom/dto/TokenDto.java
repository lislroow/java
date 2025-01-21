package spring.custom.dto;

import lombok.Data;

@Data
public class TokenDto {
  
  private TokenDto() {}
  
  @Data
  public static class CreateRes {
    private String rtk;
  }
  
  @Data
  public static class RefreshReq {
    private String rtk;
  }
  
  @Data
  public static class RefreshRes {
    private String rtk;
    private String atk;
    private Integer session;
  }
  
  @Data
  public static class VerifyReq {
    private String atk;
    private String clientIdent;
  }
  
}
