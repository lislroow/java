package spring.sample.app.code;

public enum CD_REQ_RES {
  
  REQ("1", "요청"),
  RES("2", "응답");
  
  private String code;
  private String desc;
  
  private CD_REQ_RES(String code, String desc) {
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
