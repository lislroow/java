package spring.custom.common.exception.token;

import spring.custom.common.exception.AppException;
import spring.custom.common.syscode.ERROR;

public class AccessTokenExpiredException extends AppException {
  
  private static final long serialVersionUID = -7565985742865488065L;
  
  public AccessTokenExpiredException() {
    super(ERROR.A100);
  }

}
