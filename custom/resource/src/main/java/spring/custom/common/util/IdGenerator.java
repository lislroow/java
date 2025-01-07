package spring.custom.common.util;

import java.util.Optional;
import java.util.UUID;

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
  
}
