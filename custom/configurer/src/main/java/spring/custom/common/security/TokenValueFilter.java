package spring.custom.common.security;

import java.io.IOException;
import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
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
import spring.custom.common.exception.AppException;
import spring.custom.common.syscode.ERROR;
import spring.custom.common.syscode.TOKEN;
import spring.custom.common.vo.Client;
import spring.custom.common.vo.Manager;
import spring.custom.common.vo.Member;
import spring.custom.common.vo.User;

@Slf4j
@RequiredArgsConstructor
public class TokenValueFilter extends OncePerRequestFilter {
  
  final ModelMapper modelMapper;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
    /* for debug */ if (log.isDebugEnabled()) log.info("{}", request.getRequestURL());
    
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorization != null && authorization.startsWith("Bearer ")) {
      String accessToken = authorization.substring(7);
      /* for debug */ if (log.isDebugEnabled()) log.debug("accessToken: {}", accessToken);
      JWTClaimsSet jwtClaimsSet = null;
      String roles = null;
      User principal = null;
      try {
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        jwtClaimsSet = signedJWT.getJWTClaimsSet();
        /* for debug */ if (log.isDebugEnabled()) log.debug("jwtClaimsSet: {}", jwtClaimsSet);
        
        // userType
        TOKEN.USER userType = jwtClaimsSet.toType((claims) -> {
          String val = null;
          try {
            val = claims.getStringClaim(TOKEN.CLAIM_ATTR.USER_TYPE.code());
          } catch (ParseException e) {
            throw new AppException(ERROR.A008, e);
          }
          return TOKEN.USER.byCode(val).orElseThrow(() -> new AppException(ERROR.A008));
        });
        
        // principal
        switch (userType) {
          case MEMBER -> {
            principal = jwtClaimsSet.toType((claims) -> {
              return modelMapper.map(claims.getClaim(TOKEN.CLAIM_ATTR.PRINCIPAL.code()), Member.class);
            });
          }
          case MANAGER -> {
            principal = jwtClaimsSet.toType((claims) -> {
              return modelMapper.map(claims.getClaim(TOKEN.CLAIM_ATTR.PRINCIPAL.code()), Manager.class);
            });
          }
          case CLIENT -> {
            principal = jwtClaimsSet.toType((claims) -> {
              return modelMapper.map(claims.getClaim(TOKEN.CLAIM_ATTR.PRINCIPAL.code()), Client.class);
            });
          }
          default -> {
            throw new AppException(ERROR.A008);
          }
        }
        /* for debug */ if (log.isDebugEnabled()) log.debug("principal: {}", principal);
        
        // principal
        roles = jwtClaimsSet.getStringClaim(TOKEN.CLAIM_ATTR.ROLES.code());
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
          AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    filterChain.doFilter(request, response);
  }
  
}
