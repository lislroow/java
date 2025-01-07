package spring.custom.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

public class IdGenerator {
  
  public static final String TOKEN_SEPARATOR = ":";
  
  public static String createTokenId(TOKEN.USER tokenUser) {
    String tokenId = null;
    if (tokenUser == null) {
      throw new AppException(ERROR_CODE.A001.code(), "token user is null");
    }
    tokenId = tokenUser.code() + IdGenerator.TOKEN_SEPARATOR + UUID.randomUUID().toString().replaceAll("-", "");
    return tokenId;
  }
  
  public static Optional<TOKEN.USER> getTokenUser(String tokenId) {
    if (tokenId == null) {
      throw new AppException(ERROR_CODE.A007);
    }
    return TOKEN.USER.fromCode(tokenId.split(TOKEN_SEPARATOR)[0]);
  }
  
  public static String createClientIdent() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String clientIp = XffClientIpExtractor.getClientIp(request);
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    return IdGenerator.createClientIdent(clientIp, userAgent);
  }
  
  public static String createClientIdent(String clientIp, String userAgent) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new AppException(ERROR_CODE.E901.code(), e);
    }
    byte[] encodedHash = digest.digest((clientIp + userAgent).getBytes(StandardCharsets.UTF_8));
    StringBuilder clientIdent = new StringBuilder();
    for (byte b : encodedHash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) clientIdent.append('0'); // 한 자리 수는 앞에 '0' 추가
      clientIdent.append(hex);
    }
    /* for test */ //clientIdent = new StringBuilder("a013e2bad0956321b787cf5cab9f229b02e739996b8c4c8116894a339ecdaf5c");
    return clientIdent.toString();
  }
  
  public static String createJwtRedisKey(TOKEN.JWT jwtType, String tokenId, String clientIdent) {
    String redisKey = null;
    switch (jwtType) {
    case REFRESH_TOKEN:
      redisKey = String.format("token:rtk:%s:%s", tokenId, clientIdent);
      break;
    case ACCESS_TOKEN:
      redisKey = String.format("token:atk:%s:%s", tokenId, clientIdent);
      break;
    default:
      throw new AppException(ERROR_CODE.A001);
    }
    return redisKey;
  }
  
}
