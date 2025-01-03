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
import java.util.UUID;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
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
import spring.auth.config.properties.JwtProperties;
import spring.custom.common.enumcode.RESPONSE;
import spring.custom.common.enumcode.Role;
import spring.custom.common.exception.AppException;
import spring.custom.common.redis.RedisSupport;
import spring.custom.common.vo.AuthPrincipal;
import spring.custom.dto.TokenResDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {
  
  final RedisSupport redisSupport;
  final JwtProperties jwtProperties;
  
  private Long refreshTokenExpTm;
  private Long accessTokenExpTm;
  
  private JWSSigner signer;
  private RSASSAVerifier verifier;

  @PostConstruct
  public void init() throws CertificateException, IOException {
    RSAPublicKey publicKey = loadRSAPublicKey(jwtProperties.getCert().getPublicKeyFilePath());
    this.verifier = new RSASSAVerifier(publicKey);
    
    RSAPrivateKey privateKey = loadRSAPrivateKey(jwtProperties.getCert().getPrivateKeyFilePath());
    this.signer = new RSASSASigner(privateKey);
    this.refreshTokenExpTm = jwtProperties.getToken().getRefreshTokenExpireTime();
    this.accessTokenExpTm = jwtProperties.getToken().getRefreshTokenExpireTime();
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
  
  public TokenResDto.Create createToken(org.springframework.security.core.Authentication authentication, String ip, String userAgent) {
    AuthPrincipal authPrincipal = (AuthPrincipal) authentication.getPrincipal();
    String username = authPrincipal.getUsername();
    String rtkUuid = UUID.randomUUID().toString().replaceAll("-", "");
    String atkUuid = UUID.randomUUID().toString().replaceAll("-", "");
    String clientIdent = this.sha256(ip + userAgent);
    TokenResDto.Create result = new TokenResDto.Create();
    result.setRtkUuid(rtkUuid);
    result.setAtkUuid(atkUuid);
    try {
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;
      String redisKey;
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + refreshTokenExpTm * 1000))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      redisKey = String.format("token:rtk:%s:%s", rtkUuid, clientIdent);
      Duration ttl = Duration.ofSeconds(refreshTokenExpTm);
      this.redisSupport.setValue(redisKey, signedJWT.serialize(), ttl); // refreshToken 의 expireTime 을 ttl 로 설정
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + accessTokenExpTm * 1000))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      redisKey = String.format("token:atk:%s:%s", atkUuid, clientIdent);
      this.redisSupport.setValue(redisKey, signedJWT.serialize(), ttl);
    } catch (Exception e) { 
      log.error("message: {}", e.getMessage());
      throw new AppException(RESPONSE.A001, e);
    }
    return result;
  }
  
  public TokenResDto.Verify verifyToken(String atkUuid, String ip, String userAgent) {
    TokenResDto.Verify resDto = new TokenResDto.Verify();
    
    String clientIdent = this.sha256(ip + userAgent);
    String redisKey = String.format("token:atk:%s:%s", atkUuid, clientIdent);
    String accessToken = this.redisSupport.getValue(redisKey);
    
    if (accessToken == null) {
      throw new AppException(RESPONSE.A003);
    }
    
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
  
  public TokenResDto.Create refreshToken(String oldRtkUuid, String ip, String userAgent) {
    String clientIdent = this.sha256(ip + userAgent);
    String oldRedisKey = String.format("token:rtk:%s:%s", oldRtkUuid, clientIdent);
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
    String newRedisKey;
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
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + refreshTokenExpTm * 1000))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      newRedisKey = String.format("token:rtk:%s:%s", newRtkUuid, clientIdent);
      Duration ttl = Duration.ofSeconds(refreshTokenExpTm);
      this.redisSupport.setValue(newRedisKey, signedJWT.serialize(), ttl); // refreshToken 의 expireTime 을 ttl 로 설정
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + accessTokenExpTm * 1000))
          .claim("role", Role.ROLE_USER.name())
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      newRedisKey = String.format("token:atk:%s:%s", newAtkUuid, clientIdent);
      this.redisSupport.setValue(newRedisKey, signedJWT.serialize(), ttl);
    } catch (Exception e) { 
      log.error("message: {}", e.getMessage());
      throw new AppException(RESPONSE.A004, e);
    }
    
    this.redisSupport.delValue(oldRedisKey);
    
    return result;
  }

  private String sha256(String plainText) {
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
    
    /* for test */ hexString = new StringBuilder("a013e2bad0956321b787cf5cab9f229b02e739996b8c4c8116894a339ecdaf5c");
    return hexString.toString();
  }
}
