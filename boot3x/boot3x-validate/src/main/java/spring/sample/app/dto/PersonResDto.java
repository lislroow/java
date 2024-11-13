package spring.sample.app.dto;

import lombok.Data;

public class PersonResDto {
  
  private PersonResDto() {}

  @Data
  public static class PersonInfo {
    private String id;
    private String name;
    private Integer age;
    private Double eyesight;
    private String email;
    private String password;
    private String confirmPassword;
    private String employCode;
    private String roleId;
    private String allowedIp;
  }
}
