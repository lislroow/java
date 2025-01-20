package spring.auth.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.common.login.TokenService;
import spring.auth.common.login.UserLoginDao;
import spring.custom.dto.TokenDto;

@RestController
@RequiredArgsConstructor
public class TokenController {
  
  final TokenService tokenService;
  final UserLoginDao userLoginDao;
  
  
  @PostMapping("/v1/token/verify")
  public TokenDto.VerifyRes verity(@RequestBody TokenDto.VerifyReq reqDto) {
    String atk = reqDto.getAtk();
    String clientIdent = reqDto.getClientIdent(); // api gateway 에서 x-forward-for 로 생성한 clientIdent 값
    
    TokenDto.VerifyRes resDto = tokenService.verifyAtk(atk, clientIdent);
    return resDto;
  }
  
  @PostMapping("/v1/token/refresh")
  public TokenDto.RefreshRes refresh(@RequestBody TokenDto.RefreshReq reqDto) {
    String rtk = reqDto.getRtk();
    
    TokenDto.RefreshRes resDto = tokenService.refreshToken(rtk);
    return resDto;
  }
  
  /*
  @PostMapping("/v1/token/create/{userType}/{id}")
  public TokenDto.CreateRes create(
      @PathVariable TOKEN.USER userType,
      @PathVariable String id) {
    
    // 사용자 확인
    AuthDetails authVo = null;
    switch (userType) {
    case MEMBER:
      authVo = userLoginDao.selectForMemberTokenById(id)
        .orElseThrow(() -> new AppException(ERROR.A003));
    case MANAGER:
      authVo = userLoginDao.selectForManagerTokenById(id)
        .orElseThrow(() -> new AppException(ERROR.A003));
      break;
    //case CLIENT:
    //  authVo = opendataAuthDao.selectById(id)
    //    .orElseThrow(() -> new AppException(ERROR.A003));
    //  break;
    }
    
    UserAuthentication userAuthentication = new UserAuthentication(userType, authVo);
    TokenDto.CreateRes resDto = tokenService.createToken(userAuthentication);
    return resDto;
  }
  */
}
