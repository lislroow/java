package spring.custom.common.exception.data;

import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;

public class DataNotFoundException extends AppException {
  
  private static final long serialVersionUID = -7565985742865488065L;
  
  public DataNotFoundException() {
    super(ERROR.S001);
  }

}
