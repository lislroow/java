package spring.auth.common.security;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.RESPONSE;
import spring.custom.common.enumcode.Role;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.redis.RedisSupport;
import spring.custom.common.vo.AuthPrincipal;
import spring.custom.dto.TokenResDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {
  
  final RedisSupport redisSupport;
  
  @Value("${auth.token.private-key-file-path:config/cert/star.develop.net.key}")
  private String PRIVATE_KEY_FILE_PATH;
  
  @Value("${auth.token.public-key-file-path:config/cert/star.develop.net.crt}")
  private String PUBLIC_KEY_FILE_PATH;
  
  @Value("${auth.token.issuer:market.develop.net}")
  private String ISSUER;
  
  @Value("${auth.token.refresh-token.expire-sec:600}")
  private Integer RTK_EXPIRE_SEC;
  private Long RTK_EXPIRE_MILLS;
  
  @Value("${auth.token.access-token.expire-sec:86400}")
  private Integer ATK_EXPIRE_SEC;
  private Long ATK_EXPIRE_MILLS;
  
  private final String REDIS_RTK_KEY_FMT = "token:rtk:%s:%s";
  private final String REDIS_ATK_KEY_FMT = "token:atk:%s:%s";
  
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
  
  public TokenResDto.Create createToken(org.springframework.security.core.Authentication authentication, String clientIp, String userAgent) {
    AuthPrincipal authPrincipal = (AuthPrincipal) authentication.getPrincipal();
    String username = authPrincipal.getUsername();
    String rtkUuid = UUID.randomUUID().toString().replaceAll("-", "");
    String atkUuid = UUID.randomUUID().toString().replaceAll("-", "");
    TokenResDto.Create result = new TokenResDto.Create();
    result.setRtkUuid(rtkUuid);
    result.setAtkUuid(atkUuid);
    try {
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(ISSUER)
          .expirationTime(new Date(System.currentTimeMillis() + RTK_EXPIRE_MILLS))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      this.setToken(TOKEN.REFRESH_TOKEN, rtkUuid, clientIp, userAgent, signedJWT.serialize(), Duration.ofSeconds(RTK_EXPIRE_SEC));
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(ISSUER)
          .expirationTime(new Date(System.currentTimeMillis() + ATK_EXPIRE_MILLS))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      this.setToken(TOKEN.ACCESS_TOKEN, atkUuid, clientIp, userAgent, signedJWT.serialize(), Duration.ofSeconds(ATK_EXPIRE_SEC));
    } catch (Exception e) { 
      log.error("message: {}", e.getMessage());
      throw new AppException(RESPONSE.A001, e);
    }
    return result;
  }
  
  public TokenResDto.Verify verifyToken(String atkUuid, String clientIp, String userAgent) {
    TokenResDto.Verify resDto = new TokenResDto.Verify();
    String accessToken = this.getToken(TOKEN.ACCESS_TOKEN, atkUuid, clientIp, userAgent)
        .orElseThrow(() -> new AppException(RESPONSE.A002));
    
    try {
      SignedJWT signedJWT = SignedJWT.parse(accessToken);
      if (signedJWT.verify(this.verifier)) {
        String username = signedJWT.getJWTClaimsSet().getSubject();
        resDto.setValid(true);
        resDto.setUsername(username);
      } else {
        throw new AppException(RESPONSE.A002);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(RESPONSE.A002);
    }
    return resDto;
  }
  
  public TokenResDto.Create refreshToken(String oldRtkUuid, String clientIp, String userAgent) {
    String oldRedisKey = this.getRedisKey(TOKEN.REFRESH_TOKEN, oldRtkUuid, clientIp, userAgent);
    String refreshToken = this.redisSupport.getValue(oldRedisKey);
    if (refreshToken == null) {
      throw new AppException(RESPONSE.A004);
    }
    String username = null;
    try {
      SignedJWT signedJWT = SignedJWT.parse(refreshToken);
      if (signedJWT.verify(this.verifier)) {
        username = signedJWT.getJWTClaimsSet().getSubject();
      } else {
        throw new AppException(RESPONSE.A004);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(RESPONSE.A004);
    }
    
    String newRtkUuid = UUID.randomUUID().toString().replaceAll("-", "");
    String newAtkUuid = UUID.randomUUID().toString().replaceAll("-", "");
    TokenResDto.Create result = new TokenResDto.Create();
    result.setRtkUuid(newRtkUuid);
    result.setAtkUuid(newAtkUuid);
    try {
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(ISSUER)
          .expirationTime(new Date(System.currentTimeMillis() + RTK_EXPIRE_MILLS))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      this.setToken(TOKEN.REFRESH_TOKEN, newRtkUuid, clientIp, userAgent, signedJWT.serialize(), Duration.ofSeconds(RTK_EXPIRE_SEC));
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(ISSUER)
          .expirationTime(new Date(System.currentTimeMillis() + ATK_EXPIRE_MILLS))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      this.setToken(TOKEN.ACCESS_TOKEN, newAtkUuid, clientIp, userAgent, signedJWT.serialize(), Duration.ofSeconds(ATK_EXPIRE_SEC));
    } catch (Exception e) { 
      log.error("message: {}", e.getMessage());
      throw new AppException(RESPONSE.A004, e);
    }
    this.redisSupport.delValue(oldRedisKey);
    return result;
  }
  
  private String getRedisKey(TOKEN type, String tokenUuid, String clientIp, String userAgent) {
    String redisKey = null;
    String clientIdent = this.getClientIdent(clientIp, userAgent);
    switch (type) {
    case REFRESH_TOKEN:
      redisKey = String.format(REDIS_RTK_KEY_FMT, tokenUuid, clientIdent);
      break;
    case ACCESS_TOKEN:
      redisKey = String.format(REDIS_ATK_KEY_FMT, tokenUuid, clientIdent);
      break;
    }
    return redisKey;
  }
  
  private Optional<String> getToken(TOKEN type, String tokenUuid, String clientIp, String userAgent) {
    String redisKey = this.getRedisKey(type, tokenUuid, clientIp, userAgent);
    return Optional.ofNullable(this.redisSupport.getValue(redisKey));
  }
  
  private void setToken(TOKEN type, String tokenUuid, String clientIp, String userAgent, String token, Duration ttl) {
    String redisKey = this.getRedisKey(type, tokenUuid, clientIp, userAgent);
    this.redisSupport.setValue(redisKey, token, ttl);
  }
  
  private String getClientIdent(String clientIp, String userAgent) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new AppException(RESPONSE.E002.code(), e.getMessage());
    }
    byte[] encodedHash = digest.digest((clientIp + userAgent).getBytes(StandardCharsets.UTF_8));
    StringBuilder clientIdent = new StringBuilder();
    for (byte b : encodedHash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) clientIdent.append('0'); // 한 자리 수는 앞에 '0' 추가
      clientIdent.append(hex);
    }
    /* for test */ clientIdent = new StringBuilder("a013e2bad0956321b787cf5cab9f229b02e739996b8c4c8116894a339ecdaf5c");
    return clientIdent.toString();
  }
}
