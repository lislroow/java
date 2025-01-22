package spring.auth.common.login.vo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.vo.Manager;
import spring.custom.common.vo.Member;


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
    
    public Manager toUser() {
      return Manager.builder()
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
    
    public Member toUser() {
      return Member.builder()
          .userType(TOKEN.USER.MEMBER.code())
          .id(id)
          .roles(roles)
          .loginId(loginId)
          .nickname(nickname)
          .build();
    }
    
    public LoginVo.MemberDetails toDetails() {
      Member user = Member.builder()
        .userType(TOKEN.USER.MEMBER.code())
        .id(id)
        .roles(roles)
        .loginId(loginId)
        .nickname(nickname)
        .build();
      return new LoginVo.MemberDetails(user, loginPwd);
    }
  }
  
  @Getter
  @AllArgsConstructor
  public static class MemberDetails implements UserDetails {
    private Member user;
    private String password;
    private static final long serialVersionUID = -42500550730318983L;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return user.getRoles() == null ? 
          AuthorityUtils.NO_AUTHORITIES
          : AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
    }
    
    @Override
    public String getUsername() {
      return user.getId();
    }
    
    @Override
    public String getPassword() {
      return password;
    }
  }
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberOAuth2Vo {
    private String id;
    private String oauth2Id;
    private String registrationId;
    private String email;
    private String loginId;
    private String loginPwd;
    private String roles;
    private String realname;
    private String nickname;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
    
    public LoginVo.MemberOAuth2 toOAuth2() {
      return new LoginVo.MemberOAuth2(Member.builder()
          .userType(TOKEN.USER.MEMBER.code())
          .id(id)
          .roles(roles)
          .loginId(loginId)
          .nickname(nickname)
          .build());
    }
  }
  
  @Getter
  @AllArgsConstructor
  public static class MemberOAuth2 implements OAuth2User {
    private Member user;
    
    @Override
    public Map<String, Object> getAttributes() {
      return null;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return user.getRoles() == null ? 
          AuthorityUtils.NO_AUTHORITIES
          : AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
    }
    
    @Override
    public String getName() {
      return user.getId();
    }
  }
  
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberAddVo {
    private String id;
    private String loginId;
    private String roles;
    private String email;
    private String nickname;
    private YN enableYn;
    private YN lockedYn;
    private LocalDate pwdExpDate;
  }
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberOAuth2AddVo {
    private String id;
    private String oauth2Id;
    private String registrationId;
    private String email;
    private String nickname;
  }

}