package spring.auth.common.login;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.ROLE;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;

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
      Optional<LoginVo.MemberLoginVo> optMemberLoginVo = userLoginDao.selectMemberById(optId.get());
      if (optMemberLoginVo.isPresent()) {
        return new UserAuthentication(TOKEN.USER.MEMBER, optMemberLoginVo.get());
      } else {
        throw new AppException(ERROR.A003);
      }
    } else {
      LoginVo.MemberSnsVo memberSnsVo = attributes.toMemberSnsVo();
      String id = userMngDao.selectNextId(TOKEN.USER.MEMBER.idprefix());
      memberSnsVo.setId(id);
      LoginVo.MemberRegisterVo memberRegisterVo = LoginVo.MemberRegisterVo.builder()
          .id(memberSnsVo.getId())
          .loginId(memberSnsVo.getEmail())
          .roles(ROLE.MEMBER.name())
          .nickname(memberSnsVo.getNickname())
          .enableYn(YN.Y)
          .lockedYn(YN.N)
          .build();
      userLoginDao.insertMember(memberRegisterVo);
      userLoginDao.insertMemberOauth(memberSnsVo);
      return new UserAuthentication(TOKEN.USER.MEMBER, userLoginDao.selectMemberById(id).get());
    }
  }
  
}
