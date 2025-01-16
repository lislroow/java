package spring.custom.common.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;
import spring.custom.common.vo.OpenapiVo;

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
      Map<String, Object> userAttr = null;
      String role = null;
      Object principal = null;
      try {
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        jwtClaimsSet = signedJWT.getJWTClaimsSet();
        TOKEN.USER userType = TOKEN.USER.fromCode(jwtClaimsSet.getStringClaim(TOKEN.JWT_CLAIM.USER_TYPE.code()).toString())
            .orElseThrow(() -> new AppException(ERROR.A008));
        /* for debug */ if (log.isDebugEnabled()) log.debug("jwtClaimsSet: {}", jwtClaimsSet);
        userAttr = jwtClaimsSet.getJSONObjectClaim(TOKEN.JWT_CLAIM.USER_ATTR.code());
        /* for debug */ if (log.isDebugEnabled()) log.debug("userAttr: {}", userAttr);
        switch (userType) {
        case MEMBER:
          principal = MemberVo.ofToken(userAttr);
          break;
        case MANAGER:
          principal = ManagerVo.ofToken(userAttr);
          break;
        case OPENAPI:
          principal = OpenapiVo.ofToken(userAttr);
          break;
        default:
          throw new AppException(ERROR.A008);
        }
        /* for debug */ if (log.isDebugEnabled()) log.debug("principal: {}", principal);
        role = jwtClaimsSet.getStringClaim(TOKEN.JWT_CLAIM.ROLE.code());
        /* for debug */ if (log.isDebugEnabled()) log.debug("role: {}", role);
      } catch (AppException e) {
        throw e;
      } catch (ParseException e) {
        /* for debug */ log.error("accessToken: {}", accessToken);
        /* for debug */ log.error("jwtClaimsSet: {}", jwtClaimsSet);
        /* for debug */ log.error("attributes: {}", userAttr);
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
          Collections.singleton(new SimpleGrantedAuthority(role)));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    filterChain.doFilter(request, response);
  }
  
}
