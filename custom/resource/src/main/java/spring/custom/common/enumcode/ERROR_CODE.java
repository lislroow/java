package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

public enum ERROR_CODE {
  
  /** 성공 */ S000("Success"),
  /** openfeign error */ E001("openfeign error"),
  /** encrypt error */ E002("encrypt error"),
  /** unknown error */ E999("unknown error"),
  /** api gateway error */ G001("api gateway error"),
  /** server status 503 */ G002("server status 503"),
  /** ResponseDto creation error */ G998("ResponseDto creation error"),
  /** unknown gateway error */ G999("unknown gateway error"),
  /** token creation error */ A001("token creation error"),
  /** token verification error */ A002("token verification error"),
  /** token is empty */ A003("token is empty"),
  /** token refresh error */ A004("token refresh error"),
  /** token not exist */ A005("token not exist"),
  /** login required, userId is null */ AL01("login required, userId is null"),
  /** user not found */ AL02("user not found"),
  /** illegal access */ C001("illegal access"),
  /** fail to save */ C002("fail to save")
  ;
  
  private String message;

  ERROR_CODE(String message) {
    this.message = message;
  }
  
  public String message() {
    return this.message;
  }
  
  public String code() {
    return this.name();
  }
  
  public static Optional<ERROR_CODE> fromCode(String code) {
      return Arrays.stream(ERROR_CODE.values())
          .filter(item -> item.code().equals(code))
          .findAny();
  }
  
  public String toString() {
    return String.format("[%s] %s", this.code(), this.message);
  }
  
}
