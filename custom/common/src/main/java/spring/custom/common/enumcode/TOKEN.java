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
    USER_ATTR("user_attr"),
    USER_TYPE("user_type"),
    ROLE("role")
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
    MANAGER(1, "manager"),
    MEMBER(2, "member"),
    CLIENT(3, "client"),
    GUEST(null, "guest")
    ;
    
    private Integer idprefix;
    private String code;
    
    private USER(Integer idprefix, String code) {
      this.idprefix = idprefix;
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
    
    public Integer idprefix() {
      return this.idprefix;
    }
    
    public static Optional<TOKEN.USER> fromCode(String code) {
        return Arrays.stream(TOKEN.USER.values())
            .filter(item -> item.code().equalsIgnoreCase(code))
            .findAny();
    }
  }
  
}
