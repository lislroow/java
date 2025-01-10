package spring.custom.common.exception;

import spring.custom.common.enumcode.Error;

public class AccessTokenExpiredException extends AppException {
  
  private static final long serialVersionUID = -7565985742865488065L;

  public AccessTokenExpiredException() {
    super(Error.A000);
  }

}
