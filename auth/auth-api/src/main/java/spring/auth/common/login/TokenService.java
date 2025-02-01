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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import spring.custom.code.EnumYN;
import spring.custom.common.exception.AppException;
import spring.custom.common.exception.token.AccessTokenExpiredException;
import spring.custom.common.exception.token.RefreshTokenExpiredException;
import spring.custom.common.redis.RedisClient;
import spring.custom.common.syscode.ERROR;
import spring.custom.common.syscode.TOKEN;
import spring.custom.common.vo.User;
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

  public static final String ISSUER = "develop.mgkim.net";
  public static final Integer RTK_EXPIRE_SEC = 86400;
  public static final Integer ATK_EXPIRE_SEC = 60;
  public static final Integer CLIENT_SESSION_SEC = 600;
  
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
  
  public Map.Entry<String, String> createPtk(User principal, LocalDate expDate) {
    /* for debug */ if (log.isDebugEnabled()) log.info("create permanent token: {}", principal);
    
    TOKEN.USER userType = TOKEN.USER.byCode(principal.getUserType())
        .orElseThrow(() -> new AppException(ERROR.A022));
    
    Date expiration = Date.from(expDate.plusDays(1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant());
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(principal.getId())
        .issuer(ISSUER)
        .expirationTime(expiration)
        .claim(TOKEN.CLAIM_ATTR.PRINCIPAL.code(), principal)
        .claim(TOKEN.CLAIM_ATTR.USER_TYPE.code(), principal.getUserType())
        .claim(TOKEN.CLAIM_ATTR.ROLES.code(), principal.getRoles())
        .build();
    SignedJWT signedJWT = new SignedJWT(this.header, claimsSet);
    try {
      signedJWT.sign(this.signer);
    } catch (JOSEException e) {
      throw new AppException(ERROR.A001, e);
    }
    
    String ptk = idProvider.createTokenId(userType, TOKEN.TYPE.PERMANENT_TOKEN);
    String permanentToken = signedJWT.serialize();
    Map.Entry<String, String> result = new AbstractMap.SimpleEntry<>(ptk, permanentToken);
    
    return result;
  }
  
  public Map.Entry<String, String> createRtk(User principal) {
    /* for debug */ if (log.isDebugEnabled()) log.info("create refresh token: {}", principal);
    
    TOKEN.USER userType = TOKEN.USER.byCode(principal.getUserType())
        .orElseThrow(() -> new AppException(ERROR.A022));
    
    Date expiration = Date.from(Instant.now().plusSeconds(RTK_EXPIRE_SEC));
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(principal.getId())
        .issuer(ISSUER)
        .expirationTime(expiration)
        .claim(TOKEN.CLAIM_ATTR.PRINCIPAL.code(), principal)
        .claim(TOKEN.CLAIM_ATTR.USER_TYPE.code(), principal.getUserType())
        .claim(TOKEN.CLAIM_ATTR.ROLES.code(), principal.getRoles())
        .build();
    SignedJWT signedJWT = new SignedJWT(this.header, claimsSet);
    try {
      signedJWT.sign(this.signer);
    } catch (JOSEException e) {
      throw new AppException(ERROR.A001, e);
    }
    
    String rtk = idProvider.createTokenId(userType, TOKEN.TYPE.REFRESH_TOKEN);
    String refreshToken = signedJWT.serialize();
    Map.Entry<String, String> result = new AbstractMap.SimpleEntry<>(rtk, refreshToken);
    
    this.saveRedis(rtk, refreshToken, expiration);
    
    return result;
  }
  
  public String verifyTokenId(String tokenId) {
    /* for debug */ if (log.isDebugEnabled()) log.info("verify token id: {}", tokenId);
    
    TOKEN.TYPE tokenType = idProvider.parseTokenType(tokenId)
      .orElseThrow(() -> new AppException(ERROR.A019));
    
    String tokenValue = null;
    switch (tokenType) {
      case REFRESH_TOKEN -> throw new AppException(ERROR.A019);
      case ACCESS_TOKEN -> tokenValue = this.getRedis(tokenId).orElseThrow(AccessTokenExpiredException::new);
      case PERMANENT_TOKEN -> {
        TokenVo.ClientToken clientTokenVo = tokenDao.findClientTokenByTokenKey(tokenId)
            .orElseThrow(() -> new AppException(ERROR.A005));
        // status 체크
        if (clientTokenVo.getEnableYn() != EnumYN.Y) {
          throw new AppException(ERROR.A010);
        }
        // --
        tokenValue = clientTokenVo.getTokenValue();
      }
      default -> throw new AppException(ERROR.A002);
    }
    
    try {
      SignedJWT signedJWT = SignedJWT.parse(tokenValue);
      if (signedJWT.verify(this.verifier)) {
      } else {
        throw new AppException(ERROR.A002);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(ERROR.A002, e);
    }
    return tokenValue;
  }
  
  public TokenDto.RefreshTokenRes refreshToken(String rtk) {
    /* for debug */ if (log.isDebugEnabled()) log.info("refresh token: {}", rtk);
    
    TOKEN.TYPE tokenType = idProvider.parseTokenType(rtk)
        .orElseThrow(() -> new AppException(ERROR.A019));
    if (tokenType != TOKEN.TYPE.REFRESH_TOKEN) {
      throw new AppException(ERROR.A019);
    }
    
    TOKEN.USER userType = idProvider.parseUserType(rtk)
        .orElseThrow(() -> new AppException(ERROR.A004));
    
    // verify old refresh token
    Date rtkExpiration = null;
    String subject = null;
    Map<String, Object> principal = null;
    String roles = null;
    try {
      String oldRefreshToken = this.getRedis(rtk)
          .orElseThrow(RefreshTokenExpiredException::new);
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
    String newRtk = idProvider.createTokenId(userType, TOKEN.TYPE.REFRESH_TOKEN);
    String newAtk = idProvider.createTokenId(userType, TOKEN.TYPE.ACCESS_TOKEN);
    
    TokenDto.RefreshTokenRes result = new TokenDto.RefreshTokenRes();
    result.setRtk(newRtk);
    result.setAtk(newAtk);
    result.setSession(CLIENT_SESSION_SEC);
    
    // 10 분 남았을 경우 refresh token 의 만료 시간을 연장
    Instant sessionTime = Instant.now().plusSeconds(CLIENT_SESSION_SEC);
    if (rtkExpiration.toInstant().isBefore(sessionTime)) {
      /* for debug */ if (log.isInfoEnabled())
        log.info("apply additional time to expiration time of refresh token.");
      rtkExpiration = Date.from(sessionTime);
    }
    try {
      // refreshToken 생성
      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
          .subject(subject)
          .issuer(ISSUER)
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
      Date atkExpiration = Date.from(Instant.now().plusSeconds(ATK_EXPIRE_SEC));
      claimsSet = new JWTClaimsSet.Builder()
          .subject(subject)
          .issuer(ISSUER)
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
    this.removeRedis(rtk);
    return result;
  }
  
  
  
  private void saveRedis(String tokenId, String tokenValue, Date expiration) {
    String redisKey = redisClient.getRedisKey(tokenId);
    /* for debug */ if (log.isDebugEnabled()) log.info("redisKey: {}", redisKey);
    long ttl = expiration.getTime() - System.currentTimeMillis();
    this.redisClient.setValue(redisKey, tokenValue, Duration.ofMillis(ttl));
  }
  
  private Optional<String> getRedis(String tokenId) {
    String tokenValue = null;
    String redisKey = redisClient.getRedisKey(tokenId);
    /* for debug */ if (log.isDebugEnabled()) log.info("redisKey: {}", redisKey);
    tokenValue = this.redisClient.getValue(redisKey);
    return Optional.ofNullable(tokenValue);
  }
  
  private void removeRedis(String tokenId) {
    String redisKey = redisClient.getRedisKey(tokenId);
    /* for debug */ if (log.isDebugEnabled()) log.info("redisKey: {}", redisKey);
    this.redisClient.removeValue(redisKey);
  }
  
  
  final class IdProvider {
    Optional<TOKEN.TYPE> parseTokenType(String tokenId) {
      if (ObjectUtils.isEmpty(tokenId)) {
        throw new AppException(ERROR.A009);
      }
      String cd = tokenId.substring(0, 1);
      return TOKEN.TYPE.byCd(cd);
    }
    
    Optional<TOKEN.USER> parseUserType(String tokenId) {
      if (ObjectUtils.isEmpty(tokenId)) {
        throw new AppException(ERROR.A009);
      }
      String idprefix = tokenId.substring(1, 2);
      return TOKEN.USER.byIdprefix(Integer.parseInt(idprefix));
    }
    
    String createTokenId(TOKEN.USER userType, TOKEN.TYPE tokenType) {
      if (userType == null) {
        throw new AppException(ERROR.A001.code(), "token user is null");
      }
      return String.format("%s%s%s", 
          tokenType.cd(),
          userType.idprefix(),
          UUID.randomUUID().toString().replaceAll("-", ""));
    }
  }
  
}
