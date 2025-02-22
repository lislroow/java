package spring.custom.common.syscode;

public enum AUDIT {
  
  CREATE_ID("createId"),
  MODIFY_ID("modifyId")
  ;
  
  private String field;
  private AUDIT(String field) {
    this.field = field;
  }
  
  public String getField() {
    return this.field;
  }
  
}
