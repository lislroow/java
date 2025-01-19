package spring.auth.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.UserLoginDao;
import spring.auth.api.vo.ManagerLoginVo;
import spring.auth.common.security.TokenService;
import spring.auth.common.security.UserAuthentication;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.dto.TokenDto;

@RestController
@RequiredArgsConstructor
public class ManagerLoginController {

  final UserLoginDao userLoginDao;
  final TokenService tokenService;
  final BCryptPasswordEncoder bcryptPasswordEncoder;
  
  @PostMapping("/v1/manager/login")
  public ResponseEntity<?> managerLogin(
      @RequestParam String username,
      @RequestParam String password) {
    ManagerLoginVo loginVo = userLoginDao.selectManagerByLoginId(username)
        .orElseThrow(() -> new AppException(ERROR.A003));
    if (!bcryptPasswordEncoder.matches(password, loginVo.getLoginPwd())) {
      throw new AppException(ERROR.A017);
    }
    
    UserAuthentication<?, ?> userAuthentication = new UserAuthentication(TOKEN.USER_TYPE.MANAGER, loginVo);
    TokenDto.CreateRes result = tokenService.createToken(userAuthentication);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, ResponseCookie
        .from(Constant.HTTP_HEADER.X_RTKID, result.getRtkUuid())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build().toString());
    headers.add(HttpHeaders.SET_COOKIE, ResponseCookie
        .from(Constant.HTTP_HEADER.X_USRID, userAuthentication.getUsername())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build().toString());
    return ResponseEntity.ok().headers(headers).build();
  }
}
