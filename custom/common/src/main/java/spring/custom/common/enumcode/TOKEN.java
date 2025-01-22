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
    
    public static Optional<TOKEN.TYPE> byCd(String cd) {
      return Arrays.stream(TOKEN.TYPE.values())
          .filter(item -> item.cd().equalsIgnoreCase(cd))
          .findAny();
    }
  }
  
  public enum USER {
    MANAGER("manager", 1),
    MEMBER("member", 2),
    CLIENT("client", 3)
    ;
    
    private String code;
    private Integer idprefix;
    
    private USER(String code, Integer idprefix) {
      this.code = code;
      this.idprefix = idprefix;
    }
    
    public String code() {
      return this.code;
    }
    
    public Integer idprefix() {
      return this.idprefix;
    }
    
    public static Optional<TOKEN.USER> byIdprefix(Integer idprefix) {
      return Arrays.stream(TOKEN.USER.values())
          .filter(item -> item.idprefix() == idprefix)
          .findAny();
    }
    
    public static Optional<TOKEN.USER> byCode(String code) {
      return Arrays.stream(TOKEN.USER.values())
          .filter(item -> item.code().equalsIgnoreCase(code))
          .findAny();
    }
  }
  
  public enum CLAIM_ATTR {
    USER_TYPE("userType"),
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
