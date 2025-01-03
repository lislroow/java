package spring.custom.common.enumcode;

public enum TOKEN {
  
  REFRESH_TOKEN("rtk"),
  ACCESS_TOKEN("atk")
  ;
  
  private String code;
  
  private TOKEN(String code) {
    this.code = code;
  }
  
  public String code() {
    return this.code;
  }
  
}
