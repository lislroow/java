package spring.auth.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.MemberDao;
import spring.auth.common.security.TokenService;
import spring.custom.common.constant.Constant;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.MemberVo;
import spring.custom.dto.MemberResDto;
import spring.custom.dto.TokenResDto;

@RestController
@RequiredArgsConstructor
public class MemberController {
  
  final TokenService tokenService;
  final MemberDao memberDao;
  final ModelMapper modelMapper;
  
  @GetMapping("/v1/member/info")
  public ResponseDto<MemberResDto.Info> info(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String atkUuid = null;
    if ((authorization.startsWith("Bearer") || authorization.startsWith("bearer"))) {
      atkUuid = authorization.substring(7);
    }
    String clientIp = request.getRemoteAddr();
    String userAgent = request.getHeader(Constant.HTTP_HEADER.USER_AGENT);
    TokenResDto.Verify result = tokenService.verifyToken(atkUuid, clientIp, userAgent);
    MemberVo vo = memberDao.selectByEmail(result.getUsername())
        .orElseThrow(() -> new AppException(ERROR_CODE.AL02));
    MemberResDto.Info resDto = modelMapper.map(vo, MemberResDto.Info.class);
    return ResponseDto.body(resDto);
  }
  
}
