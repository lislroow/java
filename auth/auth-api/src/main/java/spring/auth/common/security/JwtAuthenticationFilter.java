package spring.auth.common.security;

import java.io.IOException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  final TokenService tokenService;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      System.err.println(token);
      //UserDetails userDetails = validateTokenAndGetUserDetails(token);
      //
      //if (userDetails != null) {
      //  // 인증 설정
      //  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
      //      userDetails.getAuthorities());
      //  SecurityContextHolder.getContext().setAuthentication(authentication);
      //}
    }

    // 필터 체인 계속 진행
    filterChain.doFilter(request, response);
  }

  private UserDetails validateTokenAndGetUserDetails(String token) {
    // 토큰 검증 로직 구현
    // 예: 토큰에서 사용자 정보 추출 후 UserDetails 생성
    if ("valid-token".equals(token)) { // 예제용
      return User.withUsername("user").password("").authorities("ROLE_USER").build();
    }
    return null;
  }
}
