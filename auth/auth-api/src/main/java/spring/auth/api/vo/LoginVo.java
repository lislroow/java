package spring.auth.api.vo;

import java.time.LocalDate;

import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.enumcode.YN;
import spring.custom.common.security.LoginDetails;
import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;


public class LoginVo {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ManagerLoginVo implements LoginDetails<ManagerVo> {
  
    private static final long serialVersionUID = -5566207645876050761L;
    
    private String id;
    private String loginId;
    private String loginPwd;
    private String mgrName;
    private String roles;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
    
    @Override
    public String getUsername() {
      return this.id;
    }
    
    @Override
    public String getPassword() {
      return this.loginPwd;
    }
    
    @Override
    public ManagerVo toPrincipal() {
      ManagerVo memberVo = ManagerVo.builder()
          .id(id)
          .roles(roles)
          .loginId(loginId)
          .mgrName(mgrName)
          .build();
      return memberVo;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
      if (ObjectUtils.isEmpty(pwdExpDate)) {
        return true;
      }
      return LocalDate.now().isBefore(pwdExpDate) || !LocalDate.now().isEqual(pwdExpDate);
    }
    
    @Override
    public boolean isAccountNonLocked() {
      return lockedYn.compareTo(YN.N) == 0;
    }
  
    @Override
    public boolean isEnabled() {
      return enableYn.compareTo(YN.Y) == 0;
    }
    
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberLoginVo implements LoginDetails<MemberVo> {
    
    private static final long serialVersionUID = -5171902992965983740L;
    
    private String id;
    private String loginId;
    private String loginPwd;
    private String roles;
    private String realname;
    private String registrationId;
    private String oauth2Id;
    private String nickname;
    private String ip;
    private String userAgent;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
    
    @Override
    public String getUsername() {
      return this.id;
    }
    
    @Override
    public String getPassword() {
      return this.loginPwd;
    }
    
    @Override
    public MemberVo toPrincipal() {
      MemberVo memberVo = MemberVo.builder()
          .id(id)
          .roles(roles)
          .loginId(loginId)
          .registrationId(registrationId)
          .oauth2Id(oauth2Id)
          .nickname(nickname)
          .build();
      return memberVo;
    }
    
    @Override
    public boolean isAccountNonLocked() {
      return lockedYn.compareTo(YN.N) == 0;
    }
  
    @Override
    public boolean isEnabled() {
      return enableYn.compareTo(YN.Y) == 0;
    }
    
  }

}