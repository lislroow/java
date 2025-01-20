package spring.custom.common.util;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

public class IdGenerator {
  
  public static Optional<TOKEN.USER_TYPE> parseTokenKey(String tokenKey) {
    if (ObjectUtils.isEmpty(tokenKey)) {
      throw new AppException(ERROR.A009);
    }
    String idprefix = tokenKey.substring(0, 1);
    return TOKEN.USER_TYPE.fromIdprefix(Integer.parseInt(idprefix));
  }
  
  public static String createTokenKey(TOKEN.USER_TYPE tokenUser) {
    String tokenId = null;
    if (tokenUser == null) {
      throw new AppException(ERROR.A001.code(), "token user is null");
    }
    tokenId = tokenUser.idprefix() + UUID.randomUUID().toString().replaceAll("-", "");
    return tokenId;
  }
  
  public static String createClientIdent() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String clientIp = XffClientIpExtractor.getClientIp(request);
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    return IdGenerator.createClientIdent(clientIp, userAgent);
  }
  
  public static String createClientIdent(String clientIp, String userAgent) {
    String clientIdent;
    try {
      clientIdent = HashUtil.sha256(clientIp + userAgent);
    } catch (NoSuchAlgorithmException e) {
      throw new AppException(ERROR.E901.code(), e);
    }
    return clientIdent;
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
      throw new AppException(ERROR.A001);
    }
    return redisKey;
  }
  
}
