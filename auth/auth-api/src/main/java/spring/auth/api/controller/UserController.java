package spring.auth.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.UserDao;
import spring.auth.api.dto.UserDto;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.TokenPrincipal;

@RestController
@RequiredArgsConstructor
public class UserController {
  
  final UserDao userDao;
  final ModelMapper modelMapper;
  
  @GetMapping("/v1/user/info")
  public UserDto.InfoRes info() {
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
      result = userDao.selectManagerById(authentication.getName())
        .orElseThrow(() -> new AppException(ERROR.A003));
      break;
    case MEMBER:
      result = userDao.selectMemberById(authentication.getName())
        .orElseThrow(() -> new AppException(ERROR.A003));
      break;
    case CLIENT:
    default:
      throw new AppException(ERROR.A403);
    }
    UserDto.InfoRes resDto = modelMapper.map(result, UserDto.InfoRes.class);
    return resDto;
  }
  
}
