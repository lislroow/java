package spring.auth.common.login.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import spring.auth.common.login.dao.UserDao;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.ManagerPrincipal;
import spring.custom.common.vo.MemberPrincipal;

@RestController
@RequiredArgsConstructor
public class UserController {
  
  final UserDao userDao;
  final ModelMapper modelMapper;
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class InfoRes {
    private String id;
    private String roles;
    private String loginId;
    private String username;
  }
  
  @GetMapping("/v1/user/info")
  public InfoRes info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    InfoRes resDto;
    if (authentication.getPrincipal() instanceof ManagerPrincipal managerVo) {
      resDto = InfoRes.builder()
          .id(managerVo.getId())
          .roles(managerVo.getRoles())
          .loginId(managerVo.getLoginId())
          .username(managerVo.getMgrName())
          .build();
    } else if (authentication.getPrincipal() instanceof MemberPrincipal memberVo) {
      resDto = InfoRes.builder()
          .id(memberVo.getId())
          .roles(memberVo.getRoles())
          .loginId(memberVo.getLoginId())
          .username(memberVo.getNickname())
          .build();
    } else {
      throw new AppException(ERROR.A403);
    }
    return resDto;
  }
  
}
