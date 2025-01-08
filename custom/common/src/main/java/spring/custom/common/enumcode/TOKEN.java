package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

public class TOKEN {
  
  public enum JWT {
    REFRESH_TOKEN("rtk"),
    ACCESS_TOKEN("atk")
    ;
    
    private String code;
    
    private JWT(String code) {
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
  }
  
  public enum JWT_CLAIM {
    ATTRIBUTES("attributes"),
    USER_TYPE("userType")
    ;
    
    private String code;
    
    private JWT_CLAIM(String code) {
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
  }
  
  public enum USER {
    MEMBER("member"),
    MANAGER("manager")
    ;
    
    private String code;
    
    private USER(String code) {
      this.code = code;
    }
    public String code() {
    
      return this.code;
    }
    
    public static Optional<TOKEN.USER> fromCode(String code) {
        return Arrays.stream(TOKEN.USER.values())
            .filter(item -> item.code().equals(code))
            .findAny();
    }
  }
  
}
