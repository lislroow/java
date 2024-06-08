package spring.sample.cloud.security;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.sample.cloud.config.SecurityConfigProperties;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
  
  private AuthenticationManager authenticationManager;
  
  private final String LOGIN_URI;
  private final String SECRET_KEY;
  
  public LoginFilter(
      AuthenticationManager authenticationManager,
      SecurityConfigProperties properties) {
    super(authenticationManager);
    this.authenticationManager = authenticationManager;
    LOGIN_URI = properties.getLoginUri();
    SECRET_KEY = properties.getTokenSignkey();
    Assert.notNull(LOGIN_URI, "LOGIN_URI cannot be null");
    Assert.notNull(SECRET_KEY, "SECRET_KEY cannot be null");
    super.setFilterProcessesUrl(LOGIN_URI);
  }
  
  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

/*
curl -I -X POST --location 'http://localhost:8080/api/user/login?email=hi@mgkim.net&password=123'
*/
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password));
    return authentication;
  }
  
  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult
      ) throws IOException, ServletException {
    User user = (User) authResult.getPrincipal();
    String token = Jwts.builder()
        .claim("id", user.getId())
        .claim("email", user.getEmail())
        .signWith(getSigningKey())
        .compact();
    response.addHeader("x-token", token);
  }
  
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {
    super.unsuccessfulAuthentication(request, response, failed);
    System.err.println(failed);
  }
  
}
