package spring.custom.common.exception.token;

import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;

public class RefreshTokenExpiredException extends AppException {
  
  private static final long serialVersionUID = 2108551733342648253L;

  public RefreshTokenExpiredException() {
    super(ERROR.A200);
  }

}