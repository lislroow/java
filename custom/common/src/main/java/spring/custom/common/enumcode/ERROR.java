package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

public enum ERROR {
  
  /** 성공 */ S000("Success"),
  
  /** data not found */ S001("data not found"),
  
  /** server unavailable */ E503("server unavailable"),
  /** encrypt error */ E901("encrypt error"),
  /** illegal access */ E902("illegal access"),
  /** fail to save */ E903("fail to save"),
  /** fail to json convert */ E904("fail to json convert"),
  /** x-forwarded-for is empty */ E905("x-forwarded-for is empty"),
  /** not available */ A906("not available"),
  
  /** access token expired */ A100("access token expired"),
  /** refresh token expired */ A200("refresh token expired"),
  /** token creation error */ A001("token creation error"),
  /** token verification error */ A002("token verification error"),
  /** user not found */ A003("user not found"),
  /** token refresh error */ A004("token refresh error"),
  /** token not exist */ A005("token not exist"),
  /** authenticate token creation error */ A006("authenticate token creation error"),
  /** token check error */ A007("token check error"),
  /** invalid token user type */ A008("invalid token user type"),
  /** invalid token id */ A009("invalid user id"),
  /** account is disabled */ A010("account is disabled"),
  /** account is locked */ A011("account is locked"),
  /** credentials expired */ A012("credentials expired"),
  /** current password not matched */ A013("current password not matched"),
  /** confirm password not matched */ A014("confirm password not matched"),
  /** send register code error */ A015("send register code error"),
  /** invalid register code */ A016("invalid register code"),
  /** bad credentials */ A017("bad credentials"),
  /** already registered loginId */ A018("already registered"),
  
  /** access denied */ A403("access denied"),
  
  /** unknown error */ E999("unknown error")
  ;
  
  private String message;

  ERROR(String message) {
    this.message = message;
  }
  
  public String message() {
    return this.message;
  }
  
  public String code() {
    return this.name();
  }
  
  public static Optional<ERROR> fromCode(String code) {
      return Arrays.stream(ERROR.values())
          .filter(item -> item.code().equals(code))
          .findAny();
  }
  
  public static boolean isAuthError(ERROR errorCode) {
    return errorCode.code().startsWith("A");
  }
  
  public static boolean isServerError(ERROR errorCode) {
    return errorCode.code().startsWith("E");
  }
  
  public String toString() {
    return String.format("[%s] %s", this.code(), this.message);
  }
  
}
