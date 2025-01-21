package spring.auth.common.login.vo;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.auth.common.login.TokenService;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;
import spring.custom.common.security.LoginDetails;
import spring.custom.common.vo.ClientVo;
import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;


public class LoginVo {
  
  private LoginVo() { }
  
  // for login
  
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
    
    @Override
    public Long getRefreshExpireTime() {
      return System.currentTimeMillis() + TokenService.RTK_EXPIRE_MILLS;
    }
    
    @Override
    public Long getAccessExpireTime() {
      return System.currentTimeMillis() + TokenService.ATK_EXPIRE_MILLS;
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
    private String nickname;
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
    
    @Override
    public Long getRefreshExpireTime() {
      return System.currentTimeMillis() + TokenService.RTK_EXPIRE_MILLS;
    }
    
    @Override
    public Long getAccessExpireTime() {
      return System.currentTimeMillis() + TokenService.ATK_EXPIRE_MILLS;
    }
  }
  
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ClientLoginVo implements LoginDetails<ClientVo> {
  
    private static final long serialVersionUID = -671188874363049769L;
    
    private String clientId;
    private String roles;
    private LocalDate expDate;
    private String clientName;
    
    @Override
    public String getPassword() {
      return null;
    }
    
    @Override
    public String getUsername() {
      return this.clientId;
    }
    
    @Override
    public ClientVo toPrincipal() {
      ClientVo clientVo = ClientVo.builder()
          .clientId(clientId)
          .roles(roles)
          .clientName(clientName)
          .build();
      return clientVo;
    }
    
    @Override
    public Long getRefreshExpireTime() {
      return this.expDate.plusDays(1)
          .atStartOfDay(ZoneId.systemDefault())
          .toInstant()
          .toEpochMilli();
    }
    
    @Override
    public Long getAccessExpireTime() {
      throw new AppException(ERROR.E902);
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