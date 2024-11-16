package spring.sample.common.constant;

public class Constant {
  
  private Constant() {}

  public static final String BASE_PACKAGE = "spring";
  public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String ENCODING_UTF8 = "utf-8";
  public static final String ENABLED = "enabled";

  public static final String X_TOKEN_ID = "X-TOKEN-ID";
  
  public static class DBMS {
    public static final String H2 = "h2";
    public static final String MARIA = "maria";
    public static final String ORACLE = "oracle";
    public static final String VERTICA = "vertica";
  }
}
