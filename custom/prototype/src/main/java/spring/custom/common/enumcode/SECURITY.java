package spring.custom.common.enumcode;

public class SECURITY {
  
  public enum PERMIT_URI {
    
    Error("/error"),
    Actuator("/actuator/**"),
    Swagger("/v3/api-docs/**"),
    MybatisCrud("/v1/mybatis-crud/**"),
    MybatisMultipleDatasource("/v1/mybatis-multiple-datasource/**"),
    Redis("/v1/redis/**"),
    RedisSample("/v1/redis-sample/**")
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
