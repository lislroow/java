package spring.custom.common.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.ClientVo;
import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {
  
  final ModelMapper modelMapper;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorization != null && authorization.startsWith("Bearer ")) {
      String accessToken = authorization.substring(7);
      /* for debug */ if (log.isDebugEnabled()) log.debug("accessToken: {}", accessToken);
      JWTClaimsSet jwtClaimsSet = null;
      String roles = null;
      AuthenticatedPrincipal principal = null;
      try {
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        jwtClaimsSet = signedJWT.getJWTClaimsSet();
        /* for debug */ if (log.isDebugEnabled()) log.debug("jwtClaimsSet: {}", jwtClaimsSet);
        
        // userType
        TOKEN.USER_TYPE userType = jwtClaimsSet.toType((claims) -> {
          String val = null;
          try {
            val = claims.getStringClaim(TOKEN.JWT_CLAIM.USER_TYPE.code());
          } catch (ParseException e) {
            throw new AppException(ERROR.A008, e);
          }
          return TOKEN.USER_TYPE.fromCode(val).orElseThrow(() -> new AppException(ERROR.A008));
        });
        
        // principal
        switch (userType) {
        case MEMBER:
          principal = jwtClaimsSet.toType((claims) -> {
            return modelMapper.map(claims.getClaim(TOKEN.JWT_CLAIM.PRINCIPAL.code()), MemberVo.class);
          });
          break;
        case MANAGER:
          principal = jwtClaimsSet.toType((claims) -> {
            return modelMapper.map(claims.getClaim(TOKEN.JWT_CLAIM.PRINCIPAL.code()), ManagerVo.class);
          });
          break;
        case CLIENT:
          principal = jwtClaimsSet.toType((claims) -> {
            return modelMapper.map(claims.getClaim(TOKEN.JWT_CLAIM.PRINCIPAL.code()), ClientVo.class);
          });
        default:
          throw new AppException(ERROR.A008);
        }
        /* for debug */ if (log.isDebugEnabled()) log.debug("principal: {}", principal);
        
        // principal
        roles = jwtClaimsSet.getStringClaim(TOKEN.JWT_CLAIM.ROLES.code());
        /* for debug */ if (log.isDebugEnabled()) log.debug("roles: {}", roles);
      } catch (AppException e) {
        throw e;
      } catch (ParseException e) {
        /* for debug */ log.error("accessToken: {}", accessToken);
        /* for debug */ log.error("jwtClaimsSet: {}", jwtClaimsSet);
        /* for debug */ log.error("principal: {}", principal);
        /* for debug */ e.printStackTrace();
        throw new AppException(ERROR.A006, e);
      } catch (Exception e) {
        throw new AppException(ERROR.A006, e);
      }
      
      Object credentials = null;
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          principal,
          credentials,
          Arrays.stream(roles.split(","))
            .map(String::trim)
            .filter(role -> !role.isEmpty())
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList())
          );
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    filterChain.doFilter(request, response);
  }
  
}
