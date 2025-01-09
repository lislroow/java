package spring.auth.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.ManagerAuthDao;
import spring.auth.api.dao.OpendataAuthDao;
import spring.auth.common.security.TokenService;
import spring.auth.common.security.UserAuthentication;
import spring.custom.common.enumcode.Error;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.security.AuthDetails;
import spring.custom.common.util.XffClientIpExtractor;
import spring.custom.dto.TokenReqDto;
import spring.custom.dto.TokenResDto;

@RestController
@RequiredArgsConstructor
public class TokenController {
  
  final TokenService tokenService;
  final ManagerAuthDao managerAuthDao;
  final OpendataAuthDao opendataAuthDao;
  
  
  @PostMapping("/v1/token/verify")
  public TokenResDto.Verify verity(@RequestBody TokenReqDto.Verify reqDto) {
    String tokenId = reqDto.getTokenId();
    String clientIdent = reqDto.getClientIdent(); // api gateway 에서 x-forward-for 로 생성한 clientIdent 값
    
    TokenResDto.Verify resDto = tokenService.verifyToken(tokenId, clientIdent);
    return resDto;
  }
  
  @PostMapping("/v1/token/refresh")
  public TokenResDto.Refresh refresh(@RequestBody TokenReqDto.Refresh reqDto) {
    String rtkUuid = reqDto.getRtkUuid();
    
    TokenResDto.Refresh resDto = tokenService.refreshToken(rtkUuid);
    return resDto;
  }
  
  @PostMapping("/v1/token/create/{userType}/{userId}")
  public TokenResDto.Create create(
      @PathVariable TOKEN.USER userType,
      @PathVariable String userId) {
    
    // 사용자 확인
    AuthDetails authVo = null;
    switch (userType) {
    case MEMBER:
      throw new AppException(Error.A008);
    case MANAGER:
      authVo = managerAuthDao.selectById(userId)
        .orElseThrow(() -> new AppException(Error.A003));
      break;
    case OPENDATA:
      authVo = opendataAuthDao.selectById(userId)
      .orElseThrow(() -> new AppException(Error.A003));
      break;
    }

    HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    String ip = XffClientIpExtractor.getClientIp(req);
    
    UserAuthentication userAuthentication = new UserAuthentication(userType, authVo);
    TokenResDto.Create resDto = tokenService.createToken(userType, userAuthentication);
    return resDto;
  }
  
}
