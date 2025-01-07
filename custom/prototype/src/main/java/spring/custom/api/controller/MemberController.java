package spring.custom.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.MemberDao;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.MemberVo;
import spring.custom.dto.MemberResDto;

@RestController
@RequiredArgsConstructor
public class MemberController {
  
  final MemberDao memberDao;
  final ModelMapper modelMapper;
  
  @GetMapping("/v1/member/info")
  public ResponseEntity<MemberResDto.Info> info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    MemberVo result = memberDao.selectByEmail(email).orElseThrow(() -> new AppException(ERROR_CODE.A003));
    MemberResDto.Info resDto = modelMapper.map(result, MemberResDto.Info.class);
    return ResponseEntity.ok(resDto);
  }
  
}
