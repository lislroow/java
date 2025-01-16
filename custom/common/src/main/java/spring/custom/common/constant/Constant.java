package spring.custom.common.constant;

public class Constant {
  
  private Constant() {}

  public static final String BASE_PACKAGE = "spring";
  public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String ENCODING_UTF8 = "utf-8";
  public static final String ENABLED = "enabled";
  public static final String DISABLED = "disabled";
  public static final String CUSTOM = "custom";
  public static final Long MILLS = 1000L;
  
  public static class PAGE {
    public static final Integer PAGE_NUMBER = 1; // 페이지 시작은 '1' 부터
    public static final Integer PAGE_SIZE = 10;
  }
  
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
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_ATKID = "X-ATKID";
    public static final String X_RTKID = "X-RTKID";
    public static final String X_USRID = "X-USRID";
    public static final String X_RTKEX = "X-RTKEX";
    public static final String X_CAPTCHA_EC = "X-CAPTCHA-EC";
    public static final String X_CAPTCHA_ANSWER = "X-CAPTCHA-ANSWER";
  }
  
  public static class File {
    public static final String UPLOAD_BASE = "/upload";
  }
  
  public static final String LOOPBACK_IP = "127.0.0.1";
}
