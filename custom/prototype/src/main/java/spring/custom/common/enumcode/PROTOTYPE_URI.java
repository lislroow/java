package spring.custom.common.enumcode;

public enum PROTOTYPE_URI {
  
  MybatisCrud("/v1/mybatis-crud/**"),
  MybatisMultipleDatasource("/v1/mybatis-multiple-datasource/**"),
  Redis("/v1/redis/**")
  ;
  
  private String pattern;
  
  private PROTOTYPE_URI(String uriPattern) {
    this.pattern = uriPattern;
  }
  
  public String getPattern() {
    return this.pattern;
  }
}
