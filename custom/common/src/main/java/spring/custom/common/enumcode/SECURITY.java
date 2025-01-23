package spring.custom.common.enumcode;

public class SECURITY {
  
  public enum PERMIT_URI {
    
    Error("/error"),
    Actuator("/actuator/**"),
    Swagger("/v3/api-docs/**"),
    Runtime("/v1/runtime/**"),
    MybatisMultipleDatasource("/v1/mybatis-multiple-datasource/**"),
    JpaSample("/v1/jpa-sample/**"),
    MybatisSample("/v1/mybatis-sample/**"),
    RedisSample("/v1/redis-sample/**"),
    Common("/v1/common/**")
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
