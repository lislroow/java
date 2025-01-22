package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Optional;

public class TOKEN {
  
  public enum TYPE {
    PERMANENT_TOKEN("ptk", "p"),
    REFRESH_TOKEN("rtk", "r"),
    ACCESS_TOKEN("atk", "a")
    ;
    
    private String code;
    private String cd;
    
    private TYPE(String code, String cd) {
      this.cd = cd;
      this.code = code;
    }
    
    public String code() {
      return this.code;
    }
    
    public String cd() {
      return this.cd;
    }
    
    public static Optional<TOKEN.TYPE> fromCd(String cd) {
      return Arrays.stream(TOKEN.TYPE.values())
          .filter(item -> item.cd().equalsIgnoreCase(cd))
          .findAny();
    }
  }
  
  public enum USER {
    MANAGER(1),
    MEMBER(2),
    CLIENT(3),
    SNS(4),
    ;
    
    private Integer idprefix;
    
    private USER(Integer idprefix) {
      this.idprefix = idprefix;
    }
    
    public String code() {
      return this.name().toLowerCase();
    }
    
    public Integer idprefix() {
      return this.idprefix;
    }
    
    public static Optional<TOKEN.USER> fromIdprefix(Integer idprefix) {
      return Arrays.stream(TOKEN.USER.values())
          .filter(item -> item.idprefix() == idprefix)
          .findAny();
    }
    
    public static Optional<TOKEN.USER> fromCode(String code) {
      return Arrays.stream(TOKEN.USER.values())
          .filter(item -> item.code().equalsIgnoreCase(code))
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
  
}
