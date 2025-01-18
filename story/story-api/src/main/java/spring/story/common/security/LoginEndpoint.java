package spring.story.common.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.dto.TokenDto;

@RestController
@RequiredArgsConstructor
public class LoginEndpoint {
  
  final FeignTokenController feignTokenController;
  
  @Data
  public static class LoginReqDto {
    private String userId;
    private String password;
  }
  
  @PostMapping("/v1/login")
  public void login(HttpServletResponse response, @RequestBody LoginReqDto reqDto) {
    // 사용자 체크
    
    // token 생성
    TokenDto.CreateRes resDto = feignTokenController.create(TOKEN.USER.MANAGER, reqDto.userId);
    ResponseCookie rtkCookie = ResponseCookie.from(Constant.HTTP_HEADER.X_RTKID, resDto.getRtkUuid())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build();
    response.addHeader(HttpHeaders.SET_COOKIE, rtkCookie.toString());
  }
  
}
