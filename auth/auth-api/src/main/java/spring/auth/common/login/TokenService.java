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
import spring.auth.api.vo.ClientTokenVo;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;
import spring.custom.common.exception.token.AccessTokenExpiredException;
import spring.custom.common.exception.token.RefreshTokenExpiredException;
import spring.custom.common.redis.RedisSupport;
import spring.custom.common.security.LoginDetails;
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

  @Value("${auth.token.issuer:develop.mgkim.net}")
  private String ISSUER;

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
    Long expirationTime = loginVo.getRefreshExpireTime();
    JWTClaimsSet claimsSet;
    SignedJWT signedJWT;
    claimsSet = new JWTClaimsSet.Builder()
        .subject(subject)
        .issuer(ISSUER)
        .expirationTime(new Date(expirationTime))
        .claim(TOKEN.JWT_CLAIM.USER_TYPE.code(), userType.code())
        .claim(TOKEN.JWT_CLAIM.PRINCIPAL.code(), loginVo.toPrincipal())
        .claim(TOKEN.JWT_CLAIM.ROLES.code(), loginVo.getRoles())
        .build();
    
    signedJWT = new SignedJWT(this.header, claimsSet);
    try {
      signedJWT.sign(this.signer);
    } catch (JOSEException e) {
      throw new AppException(ERROR.A001, e);
    }
    
    String rtk = IdGenerator.createTokenKey(userType);
    String refreshToken = signedJWT.serialize();
    switch (userType) {
    case MANAGER, MEMBER:
      String clientIdent = IdGenerator.createClientIdent();
      String rtkRedisKey = IdGenerator.createJwtRedisKey(TOKEN.JWT.REFRESH_TOKEN, rtk, clientIdent);
      /* for debug */ if (log.isDebugEnabled())
        log.info("create token: {}", rtkRedisKey);
      this.redisSupport.setValue(rtkRedisKey, refreshToken, Duration.ofSeconds(Constant.TOKEN.RTK_EXPIRE_SEC));
      break;
    default:
      break;
    }
    
    Map.Entry<String, String> result = new AbstractMap.SimpleEntry<>(rtk, refreshToken);
    return result;
  }

  public TokenDto.VerifyRes verifyAtk(String atk, String clientIdent) {
    TOKEN.USER_TYPE userType = IdGenerator.parseTokenKey(atk)
        .orElseThrow(() -> new AppException(ERROR.A002));
    String accessToken = null;
    switch (userType) {
    case MEMBER:
    case MANAGER:
      String redisKey = IdGenerator.createJwtRedisKey(TOKEN.JWT.ACCESS_TOKEN, atk, clientIdent);
      accessToken = Optional.ofNullable(this.redisSupport.getValue(redisKey))
          .orElseThrow(() -> new AccessTokenExpiredException());
      break;
    case CLIENT:
      ClientTokenVo.VerifyToken clientTokenVo = tokenDao.findClientTokenByTokenKey(atk)
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

    TokenDto.VerifyRes resDto = new TokenDto.VerifyRes();
    try {
      SignedJWT signedJWT = SignedJWT.parse(accessToken);
      if (signedJWT.verify(this.verifier)) {
        String username = signedJWT.getJWTClaimsSet().getSubject();
        resDto.setValid(true);
        resDto.setUsername(username);
        resDto.setAccessToken(accessToken);
      } else {
        throw new AppException(ERROR.A002);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(ERROR.A002, e);
    }
    return resDto;
  }

  public TokenDto.RefreshRes refreshToken(String oldRtk) {
    TOKEN.USER_TYPE userType = IdGenerator.parseTokenKey(oldRtk)
        .orElseThrow(() -> new AppException(ERROR.A004));
    String clientIdent = IdGenerator.createClientIdent();
    String oldRtkRedis = null;
    switch (userType) {
    case MEMBER:
    case MANAGER:
      oldRtkRedis = IdGenerator.createJwtRedisKey(TOKEN.JWT.REFRESH_TOKEN, oldRtk, clientIdent);
      break;
    case CLIENT:
      throw new AppException(ERROR.A906);
    default:
      throw new AppException(ERROR.A004);
    }
    String oldRefreshToken = Optional.ofNullable(this.redisSupport.getValue(oldRtkRedis))
        .orElseThrow(() -> new RefreshTokenExpiredException());
    /* for debug */ if (log.isDebugEnabled()) log.info("refresh token (old): {}", oldRtkRedis);
    Date rtkExpireTime = null;
    String subject = null;
    Map<String, Object> userAttr = null;
    String role = null;
    try {
      SignedJWT signedJWT = SignedJWT.parse(oldRefreshToken);
      if (signedJWT.verify(this.verifier)) {
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
        rtkExpireTime = jwtClaimsSet.getExpirationTime();
        subject = signedJWT.getJWTClaimsSet().getSubject();
        userAttr = jwtClaimsSet.getJSONObjectClaim(TOKEN.JWT_CLAIM.PRINCIPAL.code());
        role = jwtClaimsSet.getStringClaim(TOKEN.JWT_CLAIM.ROLES.code());
      } else {
        throw new AppException(ERROR.A004);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(ERROR.A004, e);
    }

    String newRtk = IdGenerator.createTokenKey(userType);
    String newAtk = IdGenerator.createTokenKey(userType);

    TokenDto.RefreshRes result = new TokenDto.RefreshRes();
    result.setRtk(newRtk);
    result.setAtk(newAtk);
    result.setSession(Constant.TOKEN.CLIENT_SESSION_SEC);

    // 10 분 남았을 경우 refresh token 의 만료 시간을 연장
    Long clientSessionTime = System.currentTimeMillis() + Constant.TOKEN.CLIENT_SESSION_MILLS;
    if (clientSessionTime > rtkExpireTime.getTime()) {
      /* for debug */ if (log.isInfoEnabled())
        log.info("apply additional time to expiration time of refresh token.");
      rtkExpireTime = new Date(clientSessionTime);
    }
    try {
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;

      // refreshToken 생성
      claimsSet = new JWTClaimsSet.Builder()
          .subject(subject)
          .issuer(ISSUER)
          .expirationTime(rtkExpireTime)
          .claim(TOKEN.JWT_CLAIM.USER_TYPE.code(), userType)
          .claim(TOKEN.JWT_CLAIM.PRINCIPAL.code(), userAttr)
          .claim(TOKEN.JWT_CLAIM.ROLES.code(), role)
          .build();
      signedJWT = new SignedJWT(this.header, claimsSet);
      signedJWT.sign(this.signer);
      String newRtkRedis = IdGenerator.createJwtRedisKey(TOKEN.JWT.REFRESH_TOKEN, newRtk, clientIdent);
      long rtkExpireSec = (rtkExpireTime.getTime() - System.currentTimeMillis()) / 1000L;
      /* for debug */ if (log.isDebugEnabled())
        log.info("refresh token (new): {}", newRtkRedis);
      this.redisSupport.setValue(newRtkRedis, signedJWT.serialize(), Duration.ofSeconds(rtkExpireSec));

      // accessToken 생성
      claimsSet = new JWTClaimsSet.Builder()
          .subject(subject)
          .issuer(ISSUER)
          .expirationTime(new Date(System.currentTimeMillis() + Constant.TOKEN.ATK_EXPIRE_MILLS))
          .claim(TOKEN.JWT_CLAIM.USER_TYPE.code(), userType)
          .claim(TOKEN.JWT_CLAIM.PRINCIPAL.code(), userAttr)
          .claim(TOKEN.JWT_CLAIM.ROLES.code(), role)
          .build();
      signedJWT = new SignedJWT(this.header, claimsSet);
      signedJWT.sign(this.signer);
      String newAtkRedis = IdGenerator.createJwtRedisKey(TOKEN.JWT.ACCESS_TOKEN, newAtk, clientIdent);
      this.redisSupport.setValue(newAtkRedis, signedJWT.serialize(),
          Duration.ofSeconds(Constant.TOKEN.ATK_EXPIRE_SEC));

    } catch (Exception e) {
      throw new AppException(ERROR.A004, e);
    }
    this.redisSupport.removeValue(oldRtkRedis);
    return result;
  }

}
