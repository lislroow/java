package spring.custom.common.exception;

import java.text.MessageFormat;

import spring.custom.common.syscode.ERROR;

public class AppException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  
  private final String code;
  private final String message;

  public AppException(String code, String message) {
    super("["+code+"] " + message);
    this.code = code;
    this.message = message;
  }

  public AppException(String code, String message, Object[] args) {
    super("["+code+"] " + message);
    this.code = code;
    this.message = MessageFormat.format(message, args);
  }
  
  public AppException(String message, Throwable cause) {
    super(message, cause);
    this.code = "";
    this.message = message;
  }
  
  public AppException(ERROR error) {
    super("["+error.code()+"] " + error.message());
    this.code = error.code();
    this.message = error.message();
  }
  
  public AppException(ERROR error, Object[] args) {
    super("["+error.code()+"] " + error.message());
    this.code = error.code();
    this.message = MessageFormat.format(error.message(), args);
  }
  
  public AppException(ERROR error, Throwable cause) {
    super("["+error.code()+"] " + error.message(), cause);
    this.code = error.code();
    this.message = error.message();
  }
  
  public AppException(ERROR error, Throwable cause, Object[] args) {
    super("["+error.code()+"] " + error.message(), cause);
    this.code = error.code();
    this.message = MessageFormat.format(error.message(), args);
  }
  
  public String getErrorCode() {
    return code;
  }

  public String getErrorMessage() {
    return message;
  }
  
}