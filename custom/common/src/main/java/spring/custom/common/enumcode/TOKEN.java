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
    USER_TYPE("user_type"),
    PRINCIPAL("principal"),
    ROLES("roles")
    ;
    
    private String code;
    
    private JWT_CLAIM(String code) {
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
  }
  
  public enum USER_TYPE {
    MANAGER(1, "manager"),
    MEMBER(2, "member"),
    CLIENT(3, "client")
    ;
    
    private Integer idprefix;
    private String code;
    
    private USER_TYPE(Integer idprefix, String code) {
      this.idprefix = idprefix;
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
    
    public Integer idprefix() {
      return this.idprefix;
    }
    
    public static Optional<TOKEN.USER_TYPE> fromIdprefix(Integer idprefix) {
      return Arrays.stream(TOKEN.USER_TYPE.values())
          .filter(item -> item.idprefix() == idprefix)
          .findAny();
    }
    
    public static Optional<TOKEN.USER_TYPE> fromCode(String code) {
        return Arrays.stream(TOKEN.USER_TYPE.values())
            .filter(item -> item.code().equalsIgnoreCase(code))
            .findAny();
    }
  }
  
}
