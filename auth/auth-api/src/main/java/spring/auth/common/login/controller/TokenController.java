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
  public String verity(@RequestBody String atk) { // restTemplate 일 경우에는 기본형으로 받을 수 있음
    String accessToken = tokenService.verifyAtk(atk);
    return accessToken;
  }
  
  @PostMapping("/v1/token/refresh-token")
  public TokenDto.RefreshTokenRes refresh(@RequestBody TokenDto.RefreshTokenReq reqDto) {
    TokenDto.RefreshTokenRes resDto = tokenService.refreshToken(reqDto.getRtk());
    return resDto;
  }
  
}
