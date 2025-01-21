package spring.custom.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import spring.custom.common.constant.Constant;

public class HashUtil {
  
  public static String sha256(String plainText) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] encodedHash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
    StringBuilder hexString = new StringBuilder();
    for (byte b : encodedHash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0'); // 한 자리 수는 앞에 '0' 추가
        hexString.append(hex);
    }
    return hexString.toString();
  }

  public static String createClientIdent() throws NoSuchAlgorithmException {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String clientIp = XffClientIpExtractor.getClientIp(request);
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    return HashUtil.createClientIdent(clientIp, userAgent);
  }
  
  public static String createClientIdent(String clientIp, String userAgent) throws NoSuchAlgorithmException {
    return HashUtil.sha256(clientIp + userAgent);
  }

}
