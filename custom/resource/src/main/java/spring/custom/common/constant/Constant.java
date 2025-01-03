package spring.custom.common.constant;

public class Constant {
  
  private Constant() {}

  public static final String BASE_PACKAGE = "spring";
  public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String ENCODING_UTF8 = "utf-8";
  public static final String ENABLED = "enabled";
  public static final String CUSTOM = "custom";
  
  public static class DBMS {
    public static final String H2 = "h2";
    public static final String MARIA = "maria";
    public static final String ORACLE = "oracle";
    public static final String VERTICA = "vertica";
    public static final String POSTGRES = "postgres";
  }
  
  public static class BEAN {
    public static final String SQL_SESSION_FACTORY_BEAN = "SqlSessionFactoryBean";
    public static final String DAO = "Dao";
  }
  
  public static class HTTP_HEADER {
    public static final String USER_AGENT = "User-Agent";
    public static final String X_ATKID = "X-ATKID";
    public static final String X_RTKID = "X-RTKID";
    public static final String X_CAPTCHA_EC = "X-CAPTCHA-EC";
    public static final String X_CAPTCHA_ANSWER = "X-CAPTCHA-ANSWER";
  }
  
  public static class Token {
    public static final String ACCESS_TOKEN = "atk";
    public static final String REFRESH_TOKEN = "rtk";
  }
  
  public static class File {
    public static final String UPLOAD_BASE = "/upload";
  }
  
}
