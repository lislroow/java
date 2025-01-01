package spring.auth.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.common.security.TokenService;
import spring.custom.common.dto.TokenResDto;

@RestController
@RequiredArgsConstructor
public class TokenController {
  
  final TokenService tokenService;
  
  @GetMapping("/v1/token/verify/{token}")
  public TokenResDto.Verify verityToken(@PathVariable("token") String token) {
    TokenResDto.Verify resDto = tokenService.verifyToken(token);
    return resDto;
  }
  
}