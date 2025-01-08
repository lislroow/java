package spring.custom.common.exception;

import spring.custom.common.enumcode.Error;

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

  public AppException(Error error) {
    super("["+error.code()+"] " + error.message());
    this.code = error.code();
    this.message = error.message();
  }

  public AppException(Error error, Throwable cause) {
    super("["+error.code()+"] " + error.message(), cause);
    this.code = error.code();
    this.message = error.message();
  }

  public String getErrorCode() {
    return code;
  }

  public String getErrorMessage() {
    return message;
  }
}