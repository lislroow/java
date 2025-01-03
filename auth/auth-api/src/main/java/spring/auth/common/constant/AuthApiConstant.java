package spring.auth.common.constant;

public class AuthApiConstant {
  
  private AuthApiConstant() {}

  public static final String BASE_PACKAGE = "spring";
  public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String ENCODING_UTF8 = "utf-8";
  public static final String ENABLED = "enabled";
  
  public static class APP {
    public static final String AUTH_URI = "/auth-api";
  }
  
  public static class REDIS {
    public static final String AUTH_GUEST = "authGuest";
    public static final String AUTH_USER = "authUser";
  }
  
  public static class BEAN {
    public static final String SQL_SESSION_FACTORY_BEAN = "SqlSessionFactoryBean";
    public static final String DAO = "Dao";
  }
  
  public static class File {
    public static final String UPLOAD_BASE = "/upload";
    public static final String UPLOAD_PRODUCT = UPLOAD_BASE+"/product";
  }
  
}
