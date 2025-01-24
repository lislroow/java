package spring.custom.common.syscode;

public enum YN {
  Y, N;
  
  public String code() {
    return this.name();
  }
}
