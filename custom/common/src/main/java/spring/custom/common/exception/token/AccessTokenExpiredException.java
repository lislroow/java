package spring.custom.common.exception.token;

import spring.custom.common.enumcode.Error;
import spring.custom.common.exception.AppException;

public class AccessTokenExpiredException extends AppException {
  
  private static final long serialVersionUID = -7565985742865488065L;
  
  public AccessTokenExpiredException() {
    super(Error.A100);
  }

}
