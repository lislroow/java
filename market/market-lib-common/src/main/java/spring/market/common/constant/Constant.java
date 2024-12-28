package spring.market.common.constant;

public class Constant {
  
  private Constant() {}

  public static final String BASE_PACKAGE = "spring";
  public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String ENCODING_UTF8 = "utf-8";
  public static final String ENABLED = "enabled";
  
  public static class APP {
    public static final String AUTH_URI = "/market-api-auth";
    public static final String CUSTOMER_URI = "/market-api-customer";
    public static final String DELIVERY_URI = "/market-api-delivery";
    public static final String INVENTORY_URI = "/market-api-inventory";
    public static final String ORDER_URI = "/market-api-order";
    public static final String PRODUCT_URI = "/market-api-product";
  }

  public static class DBMS {
    public static final String H2 = "h2";
    public static final String MARIA = "maria";
    public static final String ORACLE = "oracle";
    public static final String VERTICA = "vertica";
    public static final String POSTGRES = "postgres";
  }
  
  public static class REDIS {
    public static final String AUTH_GUEST = "authGuest";
    public static final String AUTH_USER = "authUser";
  }
  
  public static class BEAN {
    public static final String SQL_SESSION_FACTORY_BEAN = "SqlSessionFactoryBean";
    public static final String DAO = "Dao";
  }
  
  public static class HTTP_HEADER {
    public static final String X_TOKEN_ID = "X-TOKEN-ID";
    public static final String X_CAPTCHA_EC = "X-CAPTCHA-EC";
    public static final String X_CAPTCHA_ANSWER = "X-CAPTCHA-ANSWER";
  }
  
  public static class Token {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
  }
  
  public static class File {
    public static final String UPLOAD_BASE = "/upload";
    public static final String UPLOAD_PRODUCT = UPLOAD_BASE+"/product";
  }
  
}
