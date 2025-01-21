package spring.custom.dto;

import lombok.Data;

@Data
public class TokenDto {
  
  private TokenDto() {}
  
  @Data
  public static class CreateTokenRes {
    private String rtk;
  }
  
  @Data
  public static class RefreshTokenReq {
    private String rtk;
  }
  
  @Data
  public static class RefreshTokenRes {
    private String rtk;
    private String atk;
    private Integer session;
  }
  
}
