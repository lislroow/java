package spring.custom.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.UserInfoDao;
import spring.custom.common.enumcode.Error;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;
import spring.custom.dto.UserInfoResDto;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
  
  final UserInfoDao memberDao;
  final ModelMapper modelMapper;
  
  @GetMapping("/v1/user/member/info")
  public UserInfoResDto.MemberInfo memberInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof UsernamePasswordAuthenticationToken &&
        authentication.getPrincipal() instanceof MemberVo)) {
      throw new AppException(Error.A403);
    }
    
    // MemberVo memberVo = (MemberVo) authentication.getPrincipal();
    // String email = memberVo.getEmail();
    String email = authentication.getName();
    MemberVo result = memberDao.selectMemberByEmail(email).orElseThrow(() -> new AppException(Error.A003));
    UserInfoResDto.MemberInfo resDto = modelMapper.map(result, UserInfoResDto.MemberInfo.class);
    return resDto;
  }
  
  @GetMapping("/v1/user/manager/info")
  public UserInfoResDto.MemberInfo managerInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof UsernamePasswordAuthenticationToken &&
        authentication.getPrincipal() instanceof ManagerVo)) {
      throw new AppException(Error.A403);
    }
    
    String mgrId = authentication.getName();
    ManagerVo result = memberDao.selectManagerByMgrId(mgrId).orElseThrow(() -> new AppException(Error.A003));
    
    // TODO
    // UserInfoResDto.ManagerInfo resDto = modelMapper.map(result, UserInfoResDto.ManagerInfo.class);
    UserInfoResDto.MemberInfo resDto = new UserInfoResDto.MemberInfo();
    resDto.setEmail(result.getMgrId());
    resDto.setNickname(result.getMgrName());
    return resDto;
  }
  
}
