package spring.custom.common.exception;

import spring.custom.common.enumcode.ERROR_CODE;

public class AppException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  
  private final String code;
  private final String message;

  public AppException(String code, String message) {
    super("["+code+"] " + message);
    this.code = code;
    this.message = message;
  }

  public AppException(String message, Throwable cause) {
    super(message, cause);
    this.code = "";
    this.message = message;
  }

  public AppException(String code) {
    super("["+code+"]");
    this.code = code;
    this.message = "";
  }

  public AppException(ERROR_CODE errorCode) {
    super("["+errorCode.code()+"] " + errorCode.message());
    this.code = errorCode.code();
    this.message = errorCode.message();
  }

  public AppException(ERROR_CODE errorCode, Throwable cause) {
    super("["+errorCode.code()+"] " + errorCode.message(), cause);
    this.code = errorCode.code();
    this.message = errorCode.message();
  }

  public String getErrorCode() {
    return code;
  }

  public String getErrorMessage() {
    return message;
  }
}