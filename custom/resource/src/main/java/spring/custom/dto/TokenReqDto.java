package spring.custom.dto;

import lombok.Data;

@Data
public class TokenReqDto {
  private TokenReqDto() {}
  
  @Data
  public static class Refresh {
    private String rtkUuid;
  }
}
