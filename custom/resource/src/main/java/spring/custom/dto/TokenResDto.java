package spring.custom.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TokenResDto implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private TokenResDto() {}
  
  @Data
  public static class Create implements Serializable {
    private static final long serialVersionUID = 1L;
    private String rtkUuid;
    private String atkUuid;
  }
  
  @Data
  public static class Verify implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean valid;
    private String username;
  }
}
