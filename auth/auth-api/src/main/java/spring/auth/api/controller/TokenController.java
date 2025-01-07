package spring.auth.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.common.security.TokenService;
import spring.custom.dto.TokenReqDto;
import spring.custom.dto.TokenResDto;

@RestController
@RequiredArgsConstructor
public class TokenController {
  
  final TokenService tokenService;
  
  @PostMapping("/v1/token/verify")
  public ResponseEntity<TokenResDto.Verify> verityToken(@RequestBody TokenReqDto.Verify reqDto) {
    String atkUuid = reqDto.getAtkUuid();
    String clientIdent = reqDto.getClientIdent(); // api gateway 에서 x-forward-for 로 생성한 clientIdent 값
    
    TokenResDto.Verify result = tokenService.verifyToken(atkUuid, clientIdent);
    return ResponseEntity.ok(result);
  }
  
  @PostMapping("/v1/token/refresh")
  public ResponseEntity<TokenResDto.Refresh> refreshToken(@RequestBody TokenReqDto.Refresh reqDto) {
    String rtkUuid = reqDto.getRtkUuid();
    
    TokenResDto.Refresh result = tokenService.refreshToken(rtkUuid);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
  
}
