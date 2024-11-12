package spring.sample.common.enumcode;

public enum SECURITY_ROLE_CD {
  ROLE_GUEST,
  ROLE_USER,
  ROLE_ADMIN;
  
  public String code() {
    return this.name();
  }
}
