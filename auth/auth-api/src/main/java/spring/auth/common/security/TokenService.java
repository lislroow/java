package spring.auth.common.security;

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
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
import spring.auth.api.dao.TokenDao;
import spring.auth.api.vo.TokenVo;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;
import spring.custom.common.exception.token.AccessTokenExpiredException;
import spring.custom.common.exception.token.RefreshTokenExpiredException;
import spring.custom.common.redis.RedisSupport;
import spring.custom.common.util.IdGenerator;
import spring.custom.dto.TokenDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {
  
  final RedisSupport redisSupport;
  final TokenDao tokenDao;
  
  @Value("${auth.token.private-key-file-path:config/cert/star.develop.net.key}")
  private String PRIVATE_KEY_FILE_PATH;
  
  @Value("${auth.token.public-key-file-path:config/cert/star.develop.net.crt}")
  private String PUBLIC_KEY_FILE_PATH;
  
  @Value("${auth.token.issuer:market.develop.net}")
  private String ISSUER;
  
  @Value("${auth.token.refresh-token.expire-sec:86400}")
  private Integer RTK_EXPIRE_SEC;
  private Long RTK_EXPIRE_MILLS;
  
  @Value("${auth.token.access-token.expire-sec:60}")
  private Integer ATK_EXPIRE_SEC;
  private Long ATK_EXPIRE_MILLS;
  
  @Value("${auth.token.client-session-sec:600}")
  private Integer CLIENT_SESSION_SEC;
  private Long CLIENT_SESSION_MILLS;
  
  private JWSSigner signer;
  private RSASSAVerifier verifier;
  
  @PostConstruct
  public void init() throws CertificateException, IOException {
    RSAPublicKey publicKey = loadRSAPublicKey(PUBLIC_KEY_FILE_PATH);
    this.verifier = new RSASSAVerifier(publicKey);
    RSAPrivateKey privateKey = loadRSAPrivateKey(PRIVATE_KEY_FILE_PATH);
    this.signer = new RSASSASigner(privateKey);
    
    RTK_EXPIRE_MILLS = RTK_EXPIRE_SEC * Constant.MILLS;
    ATK_EXPIRE_MILLS = ATK_EXPIRE_SEC * Constant.MILLS;
    CLIENT_SESSION_MILLS = CLIENT_SESSION_SEC * Constant.MILLS;
  }
  
  private RSAPrivateKey loadRSAPrivateKey(String keyFilePath) throws IOException {
    try (FileReader keyReader = new FileReader(keyFilePath);
          PEMParser pemParser = new PEMParser(keyReader)) {
      Object object = pemParser.readObject();
      PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
      JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
      PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
      return (RSAPrivateKey) privateKey;
    }
  }
  
  private RSAPublicKey loadRSAPublicKey(String crtFilePath) throws CertificateException, IOException  {
    try (FileInputStream fis = new FileInputStream(crtFilePath)) {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);
      return (RSAPublicKey) cert.getPublicKey();
    }
  }
  
  public TokenDto.CreateRes createToken(UserAuthentication userAuthentication) {
    TOKEN.USER userType = userAuthentication.getUserType();
    String username = userAuthentication.getUsername();
    TokenDto.CreateRes result = new TokenDto.CreateRes();
    Long rtkExpTime = System.currentTimeMillis() + RTK_EXPIRE_MILLS;
    try {
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(ISSUER)
          .claim(TOKEN.JWT_CLAIM.USER_TYPE.code(), userAuthentication.getUserType().code())
          .claim(TOKEN.JWT_CLAIM.USER_ATTR.code(), userAuthentication.getUserAttr())
          .claim(TOKEN.JWT_CLAIM.ROLE.code(), userAuthentication.getRoles())
          .expirationTime(new Date(rtkExpTime))
          .build();
      
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      String rtkUuid = IdGenerator.createTokenId(userType);
      result.setRtkUuid(rtkUuid);
      String clientIdent = IdGenerator.createClientIdent();
      String rtkRedisKey = IdGenerator.createJwtRedisKey(TOKEN.JWT.REFRESH_TOKEN, rtkUuid, clientIdent);
      /* for debug */ if (log.isDebugEnabled()) log.info("create token: {}", rtkRedisKey);
      
      switch (userType) {
      case MEMBER:
      case MANAGER:
        // update redis - RTR 일 경우, 잦은 업데이트 빈도로 인해 성능 이슈가 예상됨
        //Map<String, String> hash = this.redisSupport.getHash(username);
        //hash.entrySet().forEach(entry -> {
        //  if (clientIp.equals(entry.getKey())) {
        //    this.redisSupport.removeValue(entry.getValue());
        //    this.redisSupport.removeHash(username, clientIdent);
        //  }
        //});
        this.redisSupport.setValue(rtkRedisKey, signedJWT.serialize(), Duration.ofSeconds(RTK_EXPIRE_SEC));
        //this.redisSupport.setHash(username, clientIdent, rtkRedisKey, Duration.ofSeconds(RTK_EXPIRE_SEC));
        break;
      case CLIENT:
        TokenVo tokenVo = TokenVo.builder()
          .tokenId(rtkUuid)
          .id(username)
          .token(signedJWT.serialize())
          .build();
        tokenDao.insert(tokenVo);
      }
      
    } catch (Exception e) { 
      log.error("message: {}", e);
      throw new AppException(ERROR.A001, e);
    }
    return result;
  }
  
  public TokenDto.VerifyRes verifyToken(String tokenId, String clientIdent) {
    TOKEN.USER userType = TOKEN.USER.fromCode(tokenId.split(":")[0]).orElseThrow(() -> new AppException(ERROR.A002));
    String token = null;
    switch (userType) {
    case MEMBER:
    case MANAGER:
      String redisKey = IdGenerator.createJwtRedisKey(TOKEN.JWT.ACCESS_TOKEN, tokenId, clientIdent);
      token = Optional.ofNullable(this.redisSupport.getValue(redisKey))
          .orElseThrow(() -> new AccessTokenExpiredException());
      break;
    case CLIENT:
      TokenVo tokenVo = tokenDao.findByTokenId(tokenId)
          .orElseThrow(() -> new AccessTokenExpiredException());
      if (tokenVo.getEnableYn() != YN.Y) {
        throw new AppException(ERROR.A002);
      }
      if (tokenVo.getLockedYn() != YN.N) {
        throw new AppException(ERROR.A002);
      }
      token = tokenVo.getToken();
      break;
    default:
      throw new AppException(ERROR.A002);
    }
    
    TokenDto.VerifyRes resDto = new TokenDto.VerifyRes();
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      if (signedJWT.verify(this.verifier)) {
        String username = signedJWT.getJWTClaimsSet().getSubject();
        resDto.setValid(true);
        resDto.setUsername(username);
        resDto.setAccessToken(token);
      } else {
        throw new AppException(ERROR.A002);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(ERROR.A002, e);
    }
    return resDto;
  }
  
  public TokenDto.RefreshRes refreshToken(String oldRtkUuid) {
    TOKEN.USER userType = TOKEN.USER.fromCode(oldRtkUuid.split(":")[0]).orElseThrow(() -> new AppException(ERROR.A004));
    String clientIdent = IdGenerator.createClientIdent();
    String oldRedisKey = null;
    switch (userType) {
    case MEMBER:
    case MANAGER:
      oldRedisKey = IdGenerator.createJwtRedisKey(TOKEN.JWT.REFRESH_TOKEN, oldRtkUuid, clientIdent);
      break;
    default:
      throw new AppException(ERROR.A004);
    }
    String oldRefreshToken = Optional.ofNullable(this.redisSupport.getValue(oldRedisKey))
        .orElseThrow(() -> new RefreshTokenExpiredException());
    /* for debug */ if (log.isDebugEnabled()) log.info("refresh token (old): {}", oldRedisKey);
    Date rtkExpireTime = null;
    String username = null;
    Map<String, Object> attributes = null;
    String role = null;
    try {
      SignedJWT signedJWT = SignedJWT.parse(oldRefreshToken);
      if (signedJWT.verify(this.verifier)) {
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
        rtkExpireTime = jwtClaimsSet.getExpirationTime();
        username = signedJWT.getJWTClaimsSet().getSubject();
        attributes = jwtClaimsSet.getJSONObjectClaim(TOKEN.JWT_CLAIM.USER_ATTR.code());
        role = jwtClaimsSet.getStringClaim(TOKEN.JWT_CLAIM.ROLE.code());
      } else {
        throw new AppException(ERROR.A004);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(ERROR.A004, e);
    }
    
    String newRtkUuid = IdGenerator.createTokenId(userType);
    String newAtkUuid = IdGenerator.createTokenId(userType);
    
    TokenDto.RefreshRes result = new TokenDto.RefreshRes();
    result.setRtkUuid(newRtkUuid);
    result.setAtkUuid(newAtkUuid);
    result.setClientSessionSec(CLIENT_SESSION_SEC);
    
    // 10 분 남았을 경우 refresh token 의 만료 시간을 연장
    Long clientSessionTime = System.currentTimeMillis() + CLIENT_SESSION_MILLS;
    if (clientSessionTime > rtkExpireTime.getTime()) {
      /* for debug */ if (log.isInfoEnabled()) log.info("apply additional time to expiration time of refresh token.");
      rtkExpireTime = new Date(clientSessionTime);
    }
    try {
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;
      
      // refreshToken 생성
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(ISSUER)
          .expirationTime(rtkExpireTime)
          .claim(TOKEN.JWT_CLAIM.USER_TYPE.code(), userType)
          .claim(TOKEN.JWT_CLAIM.USER_ATTR.code(), attributes)
          .claim(TOKEN.JWT_CLAIM.ROLE.code(), role)
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      String newRtkRedisKey = IdGenerator.createJwtRedisKey(TOKEN.JWT.REFRESH_TOKEN, newRtkUuid, clientIdent);
      long rtkExpireSec = (rtkExpireTime.getTime() - System.currentTimeMillis()) / 1000L;
      /* for debug */ if (log.isDebugEnabled()) log.info("refresh token (new): {}", newRtkRedisKey);
      this.redisSupport.setValue(newRtkRedisKey, signedJWT.serialize(), Duration.ofSeconds(rtkExpireSec));
      
      // accessToken 생성
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(ISSUER)
          .expirationTime(new Date(System.currentTimeMillis() + ATK_EXPIRE_MILLS))
          .claim(TOKEN.JWT_CLAIM.USER_TYPE.code(), userType)
          .claim(TOKEN.JWT_CLAIM.USER_ATTR.code(), attributes)
          .claim(TOKEN.JWT_CLAIM.ROLE.code(), role)
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      String newAtkRedisKey = IdGenerator.createJwtRedisKey(TOKEN.JWT.ACCESS_TOKEN, newAtkUuid, clientIdent);
      this.redisSupport.setValue(newAtkRedisKey, signedJWT.serialize(), Duration.ofSeconds(ATK_EXPIRE_SEC));
      
    } catch (Exception e) { 
      throw new AppException(ERROR.A004, e);
    }
    this.redisSupport.removeValue(oldRedisKey);
    return result;
  }
  
}
