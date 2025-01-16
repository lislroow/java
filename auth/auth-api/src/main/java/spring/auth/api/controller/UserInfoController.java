package spring.auth.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.UserInfoDao;
import spring.auth.api.dto.UserInfoResDto;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
  
  final UserInfoDao memberDao;
  final ModelMapper modelMapper;
  
  @GetMapping("/v1/user/member/{id}")
  @PreAuthorize("#id == authentication.name")
  public UserInfoResDto.MemberInfo memberInfo(@PathVariable String id) {
    MemberVo result = memberDao.selectMemberById(id)
        .orElseThrow(() -> new AppException(ERROR.A003));
    UserInfoResDto.MemberInfo resDto = modelMapper.map(result, UserInfoResDto.MemberInfo.class);
    return resDto;
  }
  
  @GetMapping("/v1/user/manager/{id}")
  @PreAuthorize("#id == authentication.name")
  public UserInfoResDto.MemberInfo managerInfo(@PathVariable String id) {
    ManagerVo result = memberDao.selectManagerById(id)
        .orElseThrow(() -> new AppException(ERROR.A003));
    
    UserInfoResDto.MemberInfo resDto = new UserInfoResDto.MemberInfo();
    resDto.setLoginId(result.getLoginId());
    resDto.setNickname(result.getMgrName());
    return resDto;
  }
  
}
