package spring.custom.common.exception.data;

import spring.custom.common.exception.AppException;
import spring.custom.common.syscode.ERROR;

public class DataNotFoundException extends AppException {
  
  private static final long serialVersionUID = -7565985742865488065L;
  
  public DataNotFoundException() {
    super(ERROR.S001);
  }

}
