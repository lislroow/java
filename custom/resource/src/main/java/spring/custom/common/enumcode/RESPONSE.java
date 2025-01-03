package spring.custom.common.enumcode;

import java.util.Arrays;

public enum RESPONSE {
  
  /** 성공 */
  S000("Success"),
  E001("openfeign error"),
  E002("encrypt error"),
  E999("unknown error"),
  G001("api gateway error"),
  G002("server status 503"),
  G998("ResponseDto creation error"),
  G999("unknown gateway error"),
  A001("token creation error"),
  A002("token verification error"),
  A003("token is empty"),
  A004("token refresh error"),
  AL01("login required, userId is null"),
  AL02("user not found"),
  C001("illegal access"),
  C002("fail to save")
  ;
  ;
  
  private String message;

  RESPONSE(String message) {
    this.message = message;
  }
  
  public String message() {
    return this.message;
  }
  
  public String code() {
    return this.name();
  }
  
  public static RESPONSE fromCode(String code) {
      return Arrays.stream(RESPONSE.values())
          .filter(item -> item.code().equals(code))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException(String.format("'%s' not exist.", code)));
  }
  
  public String toString() {
    return String.format("[%s] %s", this.code(), this.message);
  }
  
}
