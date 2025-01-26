package spring.custom.common.syscode;

public class SECURITY {
  
  public enum PERMIT_URI {
    
    error("/error"),
    actuator("/actuator/**"),
    swgger("/v3/api-docs/**"),
    runtime("/v1/runtime/**"),
    jpa("/v1/jpa-sample/**"),
    mybatis("/v1/mybatis-sample/**"),
    redis("/v1/redis-sample/**"),
    common("/v1/common/**"),
    excel("/excel-down/**"),
    fund("/v1/fund/**"),
    fibonacci("/v1/fibonacci/**"),
    ;
    
    private String pattern;
    
    private PERMIT_URI(String uriPattern) {
      this.pattern = uriPattern;
    }
    
    public String getPattern() {
      return this.pattern;
    }
  }

}
