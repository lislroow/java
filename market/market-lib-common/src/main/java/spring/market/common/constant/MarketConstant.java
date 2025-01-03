package spring.market.common.constant;

import spring.custom.common.constant.Constant;

public class MarketConstant {
  
  private MarketConstant() {}

  public static final String BASE_PACKAGE = "spring";
  
  public static class APP {
    public static final String AUTH_URI = "/market-api-auth";
    public static final String CUSTOMER_URI = "/market-api-customer";
    public static final String DELIVERY_URI = "/market-api-delivery";
    public static final String INVENTORY_URI = "/market-api-inventory";
    public static final String ORDER_URI = "/market-api-order";
    public static final String PRODUCT_URI = "/market-api-product";
  }

  public static class REDIS {
    public static final String AUTH_GUEST = "authGuest";
    public static final String AUTH_USER = "authUser";
  }
  
  public static class File {
    public static final String UPLOAD_PRODUCT = Constant.File.UPLOAD_BASE+"/product";
  }
  
}
