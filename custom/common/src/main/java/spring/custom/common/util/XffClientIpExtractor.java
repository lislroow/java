package spring.custom.common.util;

import spring.custom.common.enumcode.Error;
import spring.custom.common.exception.AppException;

public class XffClientIpExtractor {

  public static String getClientIp(jakarta.servlet.http.HttpServletRequest request) {
    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor == null || xForwardedFor.isEmpty()) {
      throw new AppException(Error.E905);
    }
    return xForwardedFor.split(",")[0].trim();
  }
  
  public static String getClientIp(org.springframework.http.server.reactive.ServerHttpRequest request) {
    String xForwardedFor = request.getHeaders().get("X-Forwarded-For").get(0);
    if (xForwardedFor == null || xForwardedFor.isEmpty()) {
      throw new AppException(Error.E905);
    }
    return xForwardedFor.split(",")[0].trim();
  }
}
