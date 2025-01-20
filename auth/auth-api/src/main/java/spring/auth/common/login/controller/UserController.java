package spring.auth.common.login.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import spring.auth.common.login.dao.UserDao;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.TokenPrincipal;

@RestController
@RequiredArgsConstructor
public class UserController {
  
  final UserDao userDao;
  final ModelMapper modelMapper;

  @Data
  public static class InfoRes {
    private String id;
    private String roles;
    private String loginId;
    private String username;
  }
  
  @GetMapping("/v1/user/info")
  public InfoRes info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    SecurityContextHolder.setStrategyName()
    // authentication: UsernamePasswordAuthenticationToken, OAuth2AuthenticationToken
    if (!(authentication.getPrincipal() instanceof TokenPrincipal)) {
      throw new AppException(ERROR.A403);
    }
    TokenPrincipal principal = (TokenPrincipal) authentication.getPrincipal();
    TokenPrincipal result = null;
    TOKEN.USER_TYPE userType = principal.getUserType().orElseThrow(() -> new AppException(ERROR.A403));
    switch (userType) {
    case MANAGER:
      result = userDao.selectManagerInfoById(authentication.getName())
        .orElseThrow(() -> new AppException(ERROR.A003));
      break;
    case MEMBER:
      result = userDao.selectMemberInfoById(authentication.getName())
        .orElseThrow(() -> new AppException(ERROR.A003));
      break;
    case CLIENT:
    default:
      throw new AppException(ERROR.A403);
    }
    InfoRes resDto = modelMapper.map(result, InfoRes.class);
    return resDto;
  }
  
}
