package spring.auth.common.security;

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
import spring.auth.api.dao.UserDao;
import spring.auth.api.dao.UserLoginDao;
import spring.auth.api.vo.LoginVo;
import spring.custom.common.enumcode.TOKEN;

@Service
@RequiredArgsConstructor
public class MemberOAuth2LoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  
  final UserDao userDao;
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
    LoginVo.MemberLoginVo vo = attributes.toLoginVo();
    Optional<LoginVo.MemberLoginVo> optLoginVo = userLoginDao.selectMemberByLoginId(vo.getLoginId());
    if (optLoginVo.isPresent()) {
      return new UserAuthentication(TOKEN.USER_TYPE.MEMBER, optLoginVo.get());
    }
    
    /*    
        .orElseGet(() -> {
          vo.setId(userDao.selectNextId());
          memberAuthDao.insert(vo);
          return memberAuthDao.selectByLoginId(vo.getLoginId()).orElseThrow(() -> new AppException(ERROR.A003));
        });
    */
    return null;
  }
  
}
