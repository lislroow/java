package spring.auth.common.security;

import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.MemberAuthDao;
import spring.auth.api.dao.UserInfoDao;
import spring.auth.api.vo.MemberAuthVo;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

@Service
@RequiredArgsConstructor
public class MemberOAuth2LoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  
  final UserInfoDao userInfoDao;
  final MemberAuthDao memberAuthDao;
  final ModelMapper model;
  
  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User loadedUser = delegate.loadUser(userRequest);
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    MemberOAuth2Attribute attributes = MemberOAuth2Attribute.of(registrationId, userNameAttributeName, loadedUser.getAttributes());
    MemberAuthVo vo = attributes.toMemberAuthVo();
    MemberAuthVo memberAuthVo = memberAuthDao.selectByLoginId(vo.getLoginId())
        .orElseGet(() -> {
          vo.setId(userInfoDao.selectNextId());
          memberAuthDao.insert(vo);
          return memberAuthDao.selectByLoginId(vo.getLoginId()).orElseThrow(() -> new AppException(ERROR.A003));
        });
    return new UserAuthentication(TOKEN.USER.MEMBER, memberAuthVo);
  }
  
}
