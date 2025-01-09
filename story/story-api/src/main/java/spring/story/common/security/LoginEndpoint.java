package spring.story.common.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.util.XffClientIpExtractor;
import spring.custom.dto.TokenResDto;

@RestController
@RequiredArgsConstructor
public class LoginEndpoint {
  
  final FeignTokenController feignTokenController;
  
  @Data
  public static class LoginReqDto {
    private String id;
    private String password;
  }
  
  @PostMapping("/v1/login")
  public void login(HttpServletResponse response, @RequestBody LoginReqDto reqDto) {
    // 사용자 체크
    HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    String ip = XffClientIpExtractor.getClientIp(req);
    
    // token 생성
    TokenResDto.Create resDto = feignTokenController.create(TOKEN.USER.MANAGER, reqDto.id);
    ResponseCookie rtkCookie = ResponseCookie.from(Constant.HTTP_HEADER.X_RTKID, resDto.getRtkUuid())
        .path("/")
        .httpOnly(false)
        .maxAge(10)
        .build();
    response.addHeader(HttpHeaders.SET_COOKIE, rtkCookie.toString());
  }
  
}
