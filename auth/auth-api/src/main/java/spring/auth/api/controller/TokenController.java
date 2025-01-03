package spring.auth.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import spring.auth.common.security.TokenService;
import spring.custom.common.constant.Constant;
import spring.custom.common.dto.ResponseDto;
import spring.custom.dto.TokenReqDto;
import spring.custom.dto.TokenResDto;

@RestController
@RequiredArgsConstructor
public class TokenController {
  
  final TokenService tokenService;
  
  @PostMapping("/v1/token/verify")
  public ResponseDto<TokenResDto.Verify> verityToken(@RequestBody TokenReqDto.Verify reqDto) {
    String atkUuid = reqDto.getAtkUuid();
    
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String clientIp = request.getRemoteAddr();
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    
    TokenResDto.Verify result = tokenService.verifyToken(atkUuid, clientIp, userAgent);
    return ResponseDto.body(result);
  }
  
  @PostMapping("/v1/token/refresh")
  public ResponseDto<TokenResDto.Create> refreshToken(@RequestBody TokenReqDto.Refresh reqDto) {
    String rtkUuid = reqDto.getRtkUuid();
    
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String clientIp = request.getRemoteAddr();
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    
    TokenResDto.Create result = tokenService.refreshToken(rtkUuid, clientIp, userAgent);
    return ResponseDto.body(result);
  }
  
}
