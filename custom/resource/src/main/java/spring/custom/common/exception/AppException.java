package spring.custom.common.exception;

import spring.custom.common.enumcode.ERROR_CODE;

public class AppException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  
  private final String errorCode;
  private final String errorMessage;

  public AppException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public AppException(String errorMessage, Throwable cause) {
    super(errorMessage, cause);
    this.errorMessage = errorMessage;
    this.errorCode = "";
  }

  public AppException(String errorCode) {
    super(errorCode);
    this.errorCode = errorCode;
    this.errorMessage = "";
  }

  public AppException(ERROR_CODE responseCode) {
    super(responseCode.code());
    this.errorCode = responseCode.code();
    this.errorMessage = responseCode.message();
  }

  public AppException(ERROR_CODE responseCode, Throwable cause) {
    super(responseCode.code(), cause);
    this.errorCode = responseCode.code();
    this.errorMessage = responseCode.message();
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}