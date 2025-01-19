package spring.opendata.common.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.dto.TokenDto;

@RestController
@RequiredArgsConstructor
public class TokenEndpoint {

  final FeignTokenController feignTokenController;
  
  @Data
  public static class ReqDto {
    private String userId;
  }
  
  @PostMapping("/v1/token/issue")
  // @PreAuthorize("hasRole('MANAGER') and @authorized.isOwner(#reqDto.userId)")
  public TokenDto.CreateRes issue(HttpServletResponse response, @RequestBody ReqDto reqDto) {
    // token 발행 요청 정보 조회
    
    // token 발행 처리
    TokenDto.CreateRes resDto = feignTokenController.create(TOKEN.USER.CLIENT, reqDto.userId);
    
    // token 발행 상태 갱신
    String mgrId = SecurityContextHolder.getContext().getAuthentication().getName();
    
    return resDto;
  }
  
}
