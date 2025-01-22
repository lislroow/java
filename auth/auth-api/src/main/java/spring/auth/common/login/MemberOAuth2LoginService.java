package spring.auth.common.login;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.UserMngDao;
import spring.auth.common.login.dao.UserLoginDao;
import spring.auth.common.login.vo.LoginVo;
import spring.auth.common.login.vo.LoginVo.MemberVo;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.ROLE;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.Principal;

@Service
@RequiredArgsConstructor
public class MemberOAuth2LoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  
  final UserMngDao userMngDao;
  final UserLoginDao userLoginDao;
  final ModelMapper model;
  
  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User loadedUser = delegate.loadUser(userRequest);
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    MemberOAuth2Attribute attributes = MemberOAuth2Attribute.of(registrationId, userNameAttributeName, loadedUser.getAttributes());
    
    Optional<String> optId = userLoginDao.selectMemberIdByOauth2Id(attributes.getOauth2Id());
    
    if (optId.isPresent()) {
      // select user
      MemberVo loginVo = userLoginDao.selectMemberById(optId.get())
          .orElseThrow(() -> new AppException(ERROR.A003));
      Principal principal = loginVo.toPrincipal();
      return principal;
    } else {
      // registration oauth2 authentication
      LoginVo.MemberOAuth2Vo oauth2Vo = attributes.toMemberOAuth2Vo();
      String id = userMngDao.selectNextId(TOKEN.USER.MEMBER.idprefix());
      oauth2Vo.setId(id);
      LoginVo.MemberRegisterVo registerVo = LoginVo.MemberRegisterVo.builder()
          .id(oauth2Vo.getId())
          .loginId(oauth2Vo.getEmail())
          .roles(ROLE.MEMBER.name())
          .nickname(oauth2Vo.getNickname())
          .enableYn(YN.Y)
          .lockedYn(YN.N)
          .build();
      userLoginDao.insertMember(registerVo);
      userLoginDao.insertMemberOauth(oauth2Vo);
      
      // select user
      MemberVo loginVo = userLoginDao.selectMemberById(id)
          .orElseThrow(() -> new AppException(ERROR.A003));
      
      // return 'OAuth2User'
      Principal principal = loginVo.toPrincipal();
      return principal;
    }
  }
  
}
