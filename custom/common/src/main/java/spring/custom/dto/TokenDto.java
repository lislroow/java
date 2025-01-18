package spring.custom.dto;

import lombok.Data;

@Data
public class TokenDto {
  
  private TokenDto() {}
  
  @Data
  public static class CreateRes {
    private String rtkUuid;
  }
  
  
  @Data
  public static class RefreshReq {
    private String rtkUuid;
  }
  
  @Data
  public static class RefreshRes {
    private String rtkUuid;
    private String atkUuid;
    private Integer clientSessionSec;
  }
  
  
  @Data
  public static class VerifyReq {
    private String tokenId;
    private String clientIdent;
  }
  
  @Data
  public static class VerifyRes {
    private Boolean valid;
    private String username;
    private String accessToken;
  }
  
}
