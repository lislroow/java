package spring.auth.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.UserInfoDao;
import spring.auth.api.dto.UserInfoDto;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
  
  final UserInfoDao memberDao;
  final ModelMapper modelMapper;
  
  @GetMapping("/v1/user/member/myinfo")
  public UserInfoDto.MemberRes memberMyInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof UsernamePasswordAuthenticationToken &&
        authentication.getPrincipal() instanceof MemberVo)) {
      throw new AppException(ERROR.A403);
    }
    
    MemberVo result = memberDao.selectMemberById(authentication.getName())
        .orElseThrow(() -> new AppException(ERROR.A003));
    UserInfoDto.MemberRes resDto = modelMapper.map(result, UserInfoDto.MemberRes.class);
    return resDto;
  }
  
  @GetMapping("/v1/user/manager/myinfo")
  public UserInfoDto.MemberRes managerMyInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof UsernamePasswordAuthenticationToken &&
        authentication.getPrincipal() instanceof ManagerVo)) {
      throw new AppException(ERROR.A403);
    }
    
    ManagerVo result = memberDao.selectManagerById(authentication.getName())
        .orElseThrow(() -> new AppException(ERROR.A003));
    
    UserInfoDto.MemberRes resDto = new UserInfoDto.MemberRes();
    resDto.setId(result.getId());
    resDto.setLoginId(result.getLoginId());
    resDto.setNickname(result.getMgrName());
    return resDto;
  }
  
  @GetMapping("/v1/user/member/{id}")
  @PreAuthorize("#id == authentication.name")
  public UserInfoDto.MemberRes memberInfo(@PathVariable String id) {
    MemberVo result = memberDao.selectMemberById(id)
        .orElseThrow(() -> new AppException(ERROR.A003));
    UserInfoDto.MemberRes resDto = modelMapper.map(result, UserInfoDto.MemberRes.class);
    return resDto;
  }
  
  @GetMapping("/v1/user/manager/{id}")
  @PreAuthorize("#id == authentication.name")
  public UserInfoDto.MemberRes managerInfo(@PathVariable String id) {
    ManagerVo result = memberDao.selectManagerById(id)
        .orElseThrow(() -> new AppException(ERROR.A003));
    
    UserInfoDto.MemberRes resDto = new UserInfoDto.MemberRes();
    resDto.setId(result.getId());
    resDto.setLoginId(result.getLoginId());
    resDto.setNickname(result.getMgrName());
    return resDto;
  }
  
}
