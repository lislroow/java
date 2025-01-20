package spring.auth.common.login;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.vo.LoginVo;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

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
    LoginVo.ManagerLoginVo managerLoginVo = userLoginDao.selectManagerByLoginId(username)
        .orElseThrow(() -> new AppException(ERROR.A003));
    if (!bcryptPasswordEncoder.matches(password, managerLoginVo.getLoginPwd())) {
      throw new AppException(ERROR.A017);
    }
    
    Map.Entry<String, String> refreshToken = 
        tokenService.createRtk(TOKEN.USER_TYPE.MANAGER, managerLoginVo);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, ResponseCookie
        .from(Constant.HTTP_HEADER.X_RTK, refreshToken.getKey())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build().toString());
    return ResponseEntity.ok().headers(headers).build();
  }
}
