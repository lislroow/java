package spring.auth.common.login.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.vo.ManagerPrincipal;
import spring.custom.common.vo.MemberPrincipal;


public class LoginVo {
  
  private LoginVo() { }
  
  // for login
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ManagerVo {
    private String id;
    private String loginId;
    private String loginPwd;
    private String mgrName;
    private String roles;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
    
    public ManagerPrincipal toPrincipal() {
      return ManagerPrincipal.builder()
          .userType(TOKEN.USER.MANAGER.code())
          .id(id)
          .roles(roles)
          .loginId(loginId)
          .mgrName(mgrName)
          .build();
    }
  }
  
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberVo {
    private String id;
    private String loginId;
    private String loginPwd;
    private String roles;
    private String realname;
    private String nickname;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
    
    public MemberPrincipal toPrincipal() {
      return MemberPrincipal.builder()
          .userType(TOKEN.USER.MEMBER.code())
          .id(id)
          .roles(roles)
          .loginId(loginId)
          .nickname(nickname)
          .build();
    }
  }
  
  
  // no login
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberSnsVo {
    private String id;
    private String oauth2Id;
    private String registrationId;
    private String email;
    private String nickname;
  }
  
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberRegisterVo {
    private String id;
    private String loginId;
    private String roles;
    private String email;
    private String nickname;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
  }

}