package spring.auth.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import spring.auth.common.security.TokenService;
import spring.custom.common.constant.Constant;
import spring.custom.common.util.XffClientIpExtractor;
import spring.custom.dto.TokenReqDto;
import spring.custom.dto.TokenResDto;

@RestController
@RequiredArgsConstructor
public class TokenController {
  
  final TokenService tokenService;
  
  @PostMapping("/v1/token/verify")
  public ResponseEntity<TokenResDto.Verify> verityToken(@RequestBody TokenReqDto.Verify reqDto) {
    String atkUuid = reqDto.getAtkUuid();
    String clientIdent = reqDto.getClientIdent();
    
    TokenResDto.Verify result = tokenService.verifyToken(atkUuid, clientIdent);
    return ResponseEntity.ok(result);
  }
  
  @PostMapping("/v1/token/refresh")
  public ResponseEntity<TokenResDto.Refresh> refreshToken(@RequestBody TokenReqDto.Refresh reqDto) {
    String rtkUuid = reqDto.getRtkUuid();
    
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String clientIp = XffClientIpExtractor.getClientIp(request);
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    
    TokenResDto.Refresh result = tokenService.refreshToken(rtkUuid, clientIp, userAgent);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
  
}
