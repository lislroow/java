package spring.custom.common.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.vo.AuthPrincipal;
import spring.custom.common.vo.MemberVo;

@Profile({"local", "dev"})
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  
  final ModelMapper modelMapper;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader("Authorization");

    if (authorization != null && authorization.startsWith("Bearer ")) {
      String accessToken = authorization.substring(7);
      /* for debug */ if (log.isDebugEnabled()) log.debug("accessToken: {}", accessToken);
      Map<String, Object> attributes = null;
      MemberVo memberVo = null;
      AuthPrincipal userDetails = null;
      try {
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        attributes = (Map<String, Object>) signedJWT.getJWTClaimsSet().getClaim("attributes");
        /* for debug */ if (log.isDebugEnabled()) log.debug("attributes: {}", attributes);
        memberVo = modelMapper.map(attributes, MemberVo.class);
        /* for debug */ if (log.isDebugEnabled()) log.debug("memberVo: {}", memberVo);
        userDetails = new AuthPrincipal(memberVo); 
      } catch (ParseException e) {
        log.error("accessToken: {}", accessToken);
        log.error("attributes: {}", attributes);
        log.error("memberVo: {}", memberVo);
        e.printStackTrace();
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    filterChain.doFilter(request, response);
  }
  
}
