package spring.auth.common.login;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.auth.common.login.dao.TokenDao;
import spring.auth.common.login.vo.TokenVo;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.TOKEN.USER_TYPE;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;
import spring.custom.common.exception.token.AccessTokenExpiredException;
import spring.custom.common.exception.token.RefreshTokenExpiredException;
import spring.custom.common.redis.RedisClient;
import spring.custom.common.security.LoginDetails;
import spring.custom.dto.TokenDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {

  final RedisClient redisClient;
  final TokenDao tokenDao;
  final IdProvider idProvider = new IdProvider();

  @Value("${auth.token.private-key-file-path:config/cert/star.develop.net.key}")
  private String PRIVATE_KEY_FILE_PATH;

  @Value("${auth.token.public-key-file-path:config/cert/star.develop.net.crt}")
  private String PUBLIC_KEY_FILE_PATH;

  private JWSSigner signer;
  private RSASSAVerifier verifier;
  private JWSHeader header;

  @PostConstruct
  public void init() throws CertificateException, IOException {
    RSAPublicKey publicKey = loadRSAPublicKey(PUBLIC_KEY_FILE_PATH);
    this.verifier = new RSASSAVerifier(publicKey);
    RSAPrivateKey privateKey = loadRSAPrivateKey(PRIVATE_KEY_FILE_PATH);
    this.signer = new RSASSASigner(privateKey);
    this.header = new JWSHeader.Builder(JWSAlgorithm.RS256)
        .type(JOSEObjectType.JWT)
        .build();
  }
  
  private RSAPrivateKey loadRSAPrivateKey(String keyFilePath) throws IOException {
    try (FileReader keyReader = new FileReader(keyFilePath); PEMParser pemParser = new PEMParser(keyReader)) {
      Object object = pemParser.readObject();
      PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
      JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
      PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
      return (RSAPrivateKey) privateKey;
    }
  }
  
  private RSAPublicKey loadRSAPublicKey(String crtFilePath) throws CertificateException, IOException {
    try (FileInputStream fis = new FileInputStream(crtFilePath)) {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);
      return (RSAPublicKey) cert.getPublicKey();
    }
  }
  
  public Map.Entry<String, String> createRtk(TOKEN.USER_TYPE userType, LoginDetails loginVo) {
    String subject = loginVo.getUsername();
    Date expiration = new Date(loginVo.getRefreshExpireTime());
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(subject)
        .issuer(Constant.TOKEN.ISSUER)
        .expirationTime(expiration)
        .claim(TOKEN.CLAIM_ATTR.USER_TYPE.code(), userType.code())
        .claim(TOKEN.CLAIM_ATTR.PRINCIPAL.code(), loginVo.toPrincipal())
        .claim(TOKEN.CLAIM_ATTR.ROLES.code(), loginVo.getRoles())
        .build();
    SignedJWT signedJWT = new SignedJWT(this.header, claimsSet);
    try {
      signedJWT.sign(this.signer);
    } catch (JOSEException e) {
      throw new AppException(ERROR.A001, e);
    }
    
    String rtk = idProvider.createTokenId(userType, TOKEN.TOKEN_TYPE.REFRESH_TOKEN);
    String refreshToken = signedJWT.serialize();
    Map.Entry<String, String> result = new AbstractMap.SimpleEntry<>(rtk, refreshToken);
    switch (userType) {
    case MANAGER, MEMBER:
      this.saveRedis(rtk, refreshToken, expiration);
      break;
    default:
      /* for debug */ if (log.isInfoEnabled()) log.info("skip token storing");
      break;
    }
    return result;
  }
  
  public String verifyAtk(String atk) {
    TOKEN.USER_TYPE userType = idProvider.parseUserType(atk)
        .orElseThrow(() -> new AppException(ERROR.A002));
    String accessToken = null;
    switch (userType) {
    case MANAGER, MEMBER:
      accessToken = this.getRedis(atk).orElseThrow(() -> new AccessTokenExpiredException());
      break;
    case CLIENT:
      TokenVo.ClientToken clientTokenVo = tokenDao.findClientTokenByTokenKey(atk)
          .orElseThrow(() -> new AppException(ERROR.A005));
      if (clientTokenVo.getEnableYn() != YN.Y) {
        throw new AppException(ERROR.A010);
      }
      // IP 체크: clientTokenVo.getClientIp()
      accessToken = clientTokenVo.getTokenValue();
      break;
    default:
      throw new AppException(ERROR.A002);
    }
    
    try {
      SignedJWT signedJWT = SignedJWT.parse(accessToken);
      if (signedJWT.verify(this.verifier)) {
      } else {
        throw new AppException(ERROR.A002);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(ERROR.A002, e);
    }
    return accessToken;
  }
  
  public TokenDto.RefreshTokenRes refreshToken(String oldRtk) {
    TOKEN.USER_TYPE userType = idProvider.parseUserType(oldRtk)
        .orElseThrow(() -> new AppException(ERROR.A004));
    String oldRefreshToken = null;
    switch (userType) {
    case MANAGER, MEMBER:
      oldRefreshToken = this.getRedis(oldRtk).orElseThrow(() -> new RefreshTokenExpiredException());
      /* for debug */ if (log.isDebugEnabled()) log.info("refresh token (old): {}", oldRtk);
      break;
    case CLIENT:
      throw new AppException(ERROR.A906);
    default:
      throw new AppException(ERROR.A004);
    }
    
    // verify old refresh token
    Date rtkExpiration = null;
    String subject = null;
    Map<String, Object> principal = null;
    String roles = null;
    try {
      SignedJWT signedJWT = SignedJWT.parse(oldRefreshToken);
      if (signedJWT.verify(this.verifier)) {
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
        rtkExpiration = jwtClaimsSet.getExpirationTime();
        subject = signedJWT.getJWTClaimsSet().getSubject();
        principal = jwtClaimsSet.getJSONObjectClaim(TOKEN.CLAIM_ATTR.PRINCIPAL.code());
        roles = jwtClaimsSet.getStringClaim(TOKEN.CLAIM_ATTR.ROLES.code());
      } else {
        throw new AppException(ERROR.A004);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(ERROR.A004, e);
    }
    
    // create refresh and access token
    String newRtk = idProvider.createTokenId(userType, TOKEN.TOKEN_TYPE.REFRESH_TOKEN);
    String newAtk = idProvider.createTokenId(userType, TOKEN.TOKEN_TYPE.ACCESS_TOKEN);
    
    TokenDto.RefreshTokenRes result = new TokenDto.RefreshTokenRes();
    result.setRtk(newRtk);
    result.setAtk(newAtk);
    result.setSession(Constant.TOKEN.CLIENT_SESSION_SEC);
    
    // 10 분 남았을 경우 refresh token 의 만료 시간을 연장
    Long clientSessionTime = System.currentTimeMillis() + Constant.TOKEN.CLIENT_SESSION_MILLS;
    if (clientSessionTime > rtkExpiration.getTime()) {
      /* for debug */ if (log.isInfoEnabled())
        log.info("apply additional time to expiration time of refresh token.");
      rtkExpiration = new Date(clientSessionTime);
    }
    try {
      // refreshToken 생성
      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
          .subject(subject)
          .issuer(Constant.TOKEN.ISSUER)
          .expirationTime(rtkExpiration)
          .claim(TOKEN.CLAIM_ATTR.USER_TYPE.code(), userType)
          .claim(TOKEN.CLAIM_ATTR.PRINCIPAL.code(), principal)
          .claim(TOKEN.CLAIM_ATTR.ROLES.code(), roles)
          .build();
      SignedJWT signedJWT = new SignedJWT(this.header, claimsSet);
      signedJWT.sign(this.signer);
      String newRefreshToken = signedJWT.serialize();
      this.saveRedis(newRtk, newRefreshToken, rtkExpiration);
      
      // accessToken 생성
      Date atkExpiration = new Date(System.currentTimeMillis() + Constant.TOKEN.ATK_EXPIRE_MILLS);
      claimsSet = new JWTClaimsSet.Builder()
          .subject(subject)
          .issuer(Constant.TOKEN.ISSUER)
          .expirationTime(atkExpiration)
          .claim(TOKEN.CLAIM_ATTR.USER_TYPE.code(), userType)
          .claim(TOKEN.CLAIM_ATTR.PRINCIPAL.code(), principal)
          .claim(TOKEN.CLAIM_ATTR.ROLES.code(), roles)
          .build();
      signedJWT = new SignedJWT(this.header, claimsSet);
      signedJWT.sign(this.signer);
      String newAccessToken = signedJWT.serialize();
      this.saveRedis(newAtk, newAccessToken, atkExpiration);
    } catch (Exception e) {
      throw new AppException(ERROR.A004, e);
    }
    
    // remove old refresh token
    this.removeRedis(oldRtk);
    return result;
  }
  
  
  
  private void saveRedis(String tokenId, String tokenValue, Date expiration) {
    TOKEN.USER_TYPE userType = idProvider.parseUserType(tokenId)
        .orElseThrow(() -> new AppException(ERROR.A001));
    switch (userType) {
    case MANAGER, MEMBER: {
      String redisKey = idProvider.createRedisKey(tokenId);
      /* for debug */ if (log.isDebugEnabled()) log.info("redisKey: {}", redisKey);
      long ttl = expiration.getTime() - System.currentTimeMillis();
      this.redisClient.setValue(redisKey, tokenValue, Duration.ofMillis(ttl));
      break;
    }
    default:
      /* for debug */ if (log.isInfoEnabled()) log.info("skip token storing");
      break;
    }
  }
  
  private Optional<String> getRedis(String tokenId) {
    String tokenValue = null;
    TOKEN.USER_TYPE userType = idProvider.parseUserType(tokenId)
        .orElseThrow(() -> new AppException(ERROR.A001));
    switch (userType) {
    case MANAGER, MEMBER: {
      String redisKey = idProvider.createRedisKey(tokenId);
      /* for debug */ if (log.isDebugEnabled()) log.info("redisKey: {}", redisKey);
      tokenValue = this.redisClient.getValue(redisKey);
      break;
    }
    default:
      /* for debug */ if (log.isInfoEnabled()) log.info("skip token restoring");
      break;
    }
    return Optional.of(tokenValue);
  }
  
  private void removeRedis(String tokenId) {
    TOKEN.USER_TYPE userType = idProvider.parseUserType(tokenId)
        .orElseThrow(() -> new AppException(ERROR.A001));
    switch (userType) {
    case MANAGER, MEMBER: {
      String redisKey = idProvider.createRedisKey(tokenId);
      /* for debug */ if (log.isDebugEnabled()) log.info("redisKey: {}", redisKey);
      this.redisClient.removeValue(redisKey);
      break;
    }
    default:
      /* for debug */ if (log.isInfoEnabled()) log.info("skip token clearing");
      break;
    }
  }
  
  
  final class IdProvider {
    Optional<TOKEN.TOKEN_TYPE> parseTokenType(String tokenId) {
      if (ObjectUtils.isEmpty(tokenId)) {
        throw new AppException(ERROR.A009);
      }
      String cd = tokenId.substring(0, 1);
      return TOKEN.TOKEN_TYPE.fromCd(cd);
    }
    
    Optional<TOKEN.USER_TYPE> parseUserType(String tokenId) {
      if (ObjectUtils.isEmpty(tokenId)) {
        throw new AppException(ERROR.A009);
      }
      String idprefix = tokenId.substring(1, 2);
      return TOKEN.USER_TYPE.fromIdprefix(Integer.parseInt(idprefix));
    }
    
    String createTokenId(TOKEN.USER_TYPE userType, TOKEN.TOKEN_TYPE tokenType) {
      String tokenKey = null;
      if (userType == null) {
        throw new AppException(ERROR.A001.code(), "token user is null");
      }
      tokenKey = tokenType.cd() + userType.idprefix() + UUID.randomUUID().toString().replaceAll("-", "");
      return tokenKey;
    }
    
    String createRedisKey(String tokenId) {
      String cd = tokenId.substring(0, 1);
      TOKEN.TOKEN_TYPE tokenType = TOKEN.TOKEN_TYPE.fromCd(cd)
          .orElseThrow(() -> new AppException(ERROR.A001));
      String redisKey = null;
      switch (tokenType) {
      case REFRESH_TOKEN:
        redisKey = String.format("token:rtk:%s", tokenId);
        break;
      case ACCESS_TOKEN:
        redisKey = String.format("token:atk:%s", tokenId);
        break;
      default:
        throw new AppException(ERROR.A001);
      }
      return redisKey;
    }
  }
  
}
