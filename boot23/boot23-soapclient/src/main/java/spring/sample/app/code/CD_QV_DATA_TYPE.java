package spring.sample.app.code;

public enum CD_QV_DATA_TYPE {
  
  ALNUM("1", "alphabat number"),
  NUM("2", "number"),
  ALPHA("3", "alphabat"),
  HANGLE("4", "hangle")
  ;
  
  private String code;
  private String desc;
  
  private CD_QV_DATA_TYPE(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public String getDesc() {
    return this.desc;
  }
}
