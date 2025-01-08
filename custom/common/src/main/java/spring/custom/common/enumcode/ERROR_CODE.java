package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

public enum ERROR_CODE {
  
  /** 성공 */ S000("Success"),
  
  /** server unavailable */ E503("server unavailable"),
  /** encrypt error */ E901("encrypt error"),
  /** illegal access */ E902("illegal access"),
  /** fail to save */ E903("fail to save"),
  /** fail to json convert */ E904("fail to json convert"),
  /** x-forwarded-for is empty */ E905("x-forwarded-for is empty"),
  
  /** token creation error */ A001("token creation error"),
  /** token verification error */ A002("token verification error"),
  /** user not found */ A003("user not found"),
  /** token refresh error */ A004("token refresh error"),
  /** token not exist */ A005("token not exist"),
  /** authenticate token creation error */ A006("authenticate token creation error"),
  /** token check error */ A007("token check error"),
  /** invalid token user type */ A008("invalid token user type"),
  
  /** access denied */ A403("access denied"),
  
  /** unknown error */ E999("unknown error")
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
  
  public static boolean isAuthError(ERROR_CODE errorCode) {
    return errorCode.code().startsWith("A");
  }
  
  public static boolean isServerError(ERROR_CODE errorCode) {
    return errorCode.code().startsWith("E");
  }
  
  public String toString() {
    return String.format("[%s] %s", this.code(), this.message);
  }
  
}
