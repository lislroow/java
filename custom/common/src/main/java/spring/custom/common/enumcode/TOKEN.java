package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

public class TOKEN {
  
  public enum TOKEN_TYPE {
    REFRESH_TOKEN("rtk", "a"),
    ACCESS_TOKEN("atk", "r")
    ;
    
    private String code;
    private String cd;
    
    private TOKEN_TYPE(String code, String cd) {
      this.cd = cd;
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
    
    public String cd() {
      return this.cd;
    }
    
    public static Optional<TOKEN.TOKEN_TYPE> fromCd(String cd) {
      return Arrays.stream(TOKEN.TOKEN_TYPE.values())
          .filter(item -> item.cd().equalsIgnoreCase(cd))
          .findAny();
    }
  }
  
  public enum CLAIM_ATTR {
    USER_TYPE("user_type"),
    PRINCIPAL("principal"),
    ROLES("roles")
    ;
    
    private String code;
    
    private CLAIM_ATTR(String code) {
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
  }
  
  public enum USER_TYPE {
    MANAGER(1),
    MEMBER(2),
    CLIENT(3),
    SNS(4),
    ;
    
    private Integer idprefix;
    
    private USER_TYPE(Integer idprefix) {
      this.idprefix = idprefix;
    }
    
    public String code() {
      return this.name().toLowerCase();
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
