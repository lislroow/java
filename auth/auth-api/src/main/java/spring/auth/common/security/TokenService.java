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
import java.util.UUID;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.modelmapper.internal.util.Assert;
import org.springframework.http.ResponseCookie;
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
import spring.auth.common.constant.Constant;
import spring.auth.config.properties.JwtProperties;
import spring.custom.common.dto.TokenResDto;
import spring.custom.common.enumcode.RESPONSE;
import spring.custom.common.exception.AppException;
import spring.custom.common.redis.RedisSupport;
import spring.custom.common.vo.SessionUser;

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
  
  public String createToken(org.springframework.security.core.Authentication authentication) throws Exception {
    String username = ((SessionUser)authentication.getPrincipal()).getUsername();
    log.info("create token");
    String tokenId = null;
    try {
      tokenId = UUID.randomUUID().toString();
      JWTClaimsSet claimsSet;
      SignedJWT signedJWT;
      String token;
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + refreshTokenExpTm * 1000))
          .claim("scope", "user")
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
          );
      signedJWT.sign(this.signer);
      token = signedJWT.serialize();
      Duration ttl = Duration.ofSeconds(refreshTokenExpTm);
      this.redisSupport.setHash(tokenId, Constant.Token.REFRESH_TOKEN, token, ttl); // refreshToken 의 expireTime 을 ttl 로 설정
      
      claimsSet = new JWTClaimsSet.Builder()
          .subject(username)
          .issuer(jwtProperties.getToken().getIssuer())
          .expirationTime(new Date(new Date().getTime() + accessTokenExpTm * 1000))
          .claim("scope", "user")
          .build();
      signedJWT = new SignedJWT(
          new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
          claimsSet
      );
      signedJWT.sign(this.signer);
      token = signedJWT.serialize();
      this.redisSupport.setHash(tokenId, Constant.Token.ACCESS_TOKEN, token);
    } catch (Exception e) {
      log.error("message: {}", e.getMessage());
      throw new AppException(RESPONSE.A001, e);
    }
    return tokenId;
  }
  
  public ResponseCookie createTokenCookie(org.springframework.security.core.Authentication authentication) throws Exception {
    String tokenId = this.createToken(authentication);
    return ResponseCookie.from(Constant.HTTP_HEADER.X_TOKEN_ID, tokenId)
        .path("/")
        .httpOnly(true)
        .build();
  }
  
  public TokenResDto.Verify verifyToken(String tokenId) {
    TokenResDto.Verify resDto = new TokenResDto.Verify();
    String accessToken = this.redisSupport.getHash(tokenId, Constant.Token.ACCESS_TOKEN);
    
    if (accessToken == null) {
      throw new AppException(RESPONSE.A003);
    }
    
    try {
      SignedJWT signedJWT = SignedJWT.parse(accessToken.toString());
      if (signedJWT.verify(this.verifier)) {
        String email = signedJWT.getJWTClaimsSet().getSubject();
        resDto.setUserId(email);
      } else {
        throw new AppException(RESPONSE.A002);
      }
    } catch (JOSEException | ParseException e) {
      throw new AppException(RESPONSE.A002);
    }
    return resDto;
  }
}