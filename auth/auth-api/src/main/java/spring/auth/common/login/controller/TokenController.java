package spring.auth.common.login.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.common.login.TokenService;
import spring.auth.common.login.dao.UserLoginDao;
import spring.custom.dto.TokenDto;

@RestController
@RequiredArgsConstructor
public class TokenController {
  
  final TokenService tokenService;
  final UserLoginDao userLoginDao;
  
  @PostMapping("/v1/token/verify-token")
  public String verity(@RequestBody TokenDto.VerifyReq reqDto) {
    String atk = reqDto.getAtk();
    String clientIdent = reqDto.getClientIdent(); // api gateway 에서 x-forward-for 로 생성한 clientIdent 값
    
    String accessToken = tokenService.verifyAtk(atk, clientIdent);
    return accessToken;
  }
  
  @PostMapping("/v1/token/refresh-token")
  public TokenDto.RefreshRes refresh(@RequestBody TokenDto.RefreshReq reqDto) {
    String rtk = reqDto.getRtk();
    
    TokenDto.RefreshRes resDto = tokenService.refreshToken(rtk);
    return resDto;
  }
  
}
