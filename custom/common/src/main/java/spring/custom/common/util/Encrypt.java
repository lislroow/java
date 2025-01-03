package spring.custom.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import spring.custom.common.enumcode.RESPONSE;
import spring.custom.common.exception.AppException;

public class Encrypt {

  
  /* move to TokenService */ private static String sha256(String plainText) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new AppException(RESPONSE.E002.code(), e.getMessage());
    }
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
