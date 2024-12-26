package spring.component.common.exception;

import spring.component.common.enumcode.RESPONSE_CODE;

public class BizException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  
  private final String errorCode;
  private final String errorMessage;

  public BizException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public BizException(String errorMessage, Throwable cause) {
    super(errorMessage, cause);
    this.errorMessage = errorMessage;
    this.errorCode = "";
  }

  public BizException(String errorCode) {
    super(errorCode);
    this.errorCode = errorCode;
    this.errorMessage = "";
  }

  public BizException(RESPONSE_CODE responseCode) {
    super(responseCode.code());
    this.errorCode = responseCode.code();
    this.errorMessage = responseCode.message();
  }

  public BizException(RESPONSE_CODE responseCode, Throwable cause) {
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