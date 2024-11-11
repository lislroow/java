package spring.sample.common.enumcode;

public enum RESPONSE_CODE {
  
  /** 성공 */
  S000("Success"),
  E001("openfeign error"),
  E999("unknown error"),
  ;
  
  private String message;

  RESPONSE_CODE(String message){
    this.message = message;
  }
  
  public String message() {
    return this.message;
  }
  
  public String code() {
    return this.name();
  }
}
