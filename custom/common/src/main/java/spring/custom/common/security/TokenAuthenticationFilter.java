package spring.custom.common.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.AuthPrincipal;
import spring.custom.common.vo.MemberVo;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
  
  final ModelMapper modelMapper;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader("Authorization");

    if (authorization != null && authorization.startsWith("Bearer ")) {
      String accessToken = authorization.substring(7);
      /* for debug */ if (log.isDebugEnabled()) log.debug("accessToken: {}", accessToken);
      JWTClaimsSet jwtClaimsSet = null;
      Map<String, Object> attributes = null;
      MemberVo memberVo = null;
      AuthPrincipal userDetails = null;
      try {
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        jwtClaimsSet = signedJWT.getJWTClaimsSet();
        TOKEN.USER userType = TOKEN.USER.fromCode(jwtClaimsSet.getStringClaim(TOKEN.JWT_CLAIM.USER_TYPE.code()).toString())
            .orElseThrow(() -> new AppException(ERROR_CODE.A006));
        /* for debug */ if (log.isDebugEnabled()) log.debug("jwtClaimsSet: {}", jwtClaimsSet);
        attributes = jwtClaimsSet.getJSONObjectClaim(TOKEN.JWT_CLAIM.ATTRIBUTES.code());
        /* for debug */ if (log.isDebugEnabled()) log.debug("attributes: {}", attributes);
        memberVo = modelMapper.map(attributes, MemberVo.class);
        /* for debug */ if (log.isDebugEnabled()) log.debug("memberVo: {}", memberVo);
        userDetails = new AuthPrincipal(userType, memberVo);
      } catch (AppException e) {
        throw e;
      } catch (ParseException e) {
        /* for debug */ log.error("accessToken: {}", accessToken);
        /* for debug */ log.error("jwtClaimsSet: {}", jwtClaimsSet);
        /* for debug */ log.error("attributes: {}", attributes);
        /* for debug */ log.error("memberVo: {}", memberVo);
        /* for debug */ e.printStackTrace();
        throw new AppException(ERROR_CODE.A006, e);
      } catch (Exception e) {
        throw new AppException(ERROR_CODE.A006, e);
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    filterChain.doFilter(request, response);
  }
  
}
