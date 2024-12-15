package spring.sample.common.enumcode;

public enum YN {
  Y, N;
  
  public String code() {
    return this.name();
  }
}
