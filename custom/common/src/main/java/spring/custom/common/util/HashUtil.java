package spring.custom.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
  
}
