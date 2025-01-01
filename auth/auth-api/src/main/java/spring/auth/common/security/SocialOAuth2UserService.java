package spring.auth.common.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.MemberDao;
import spring.custom.common.vo.MemberVo;
import spring.custom.common.vo.SessionUser;

@Service
@RequiredArgsConstructor
public class SocialOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  
  final MemberDao memberDao;
  //final UserRepository userRepository;
  final ModelMapper model;
  final BCryptPasswordEncoder bcryptPasswordEncoder;
  
  @Value("${auth.social.user.defaultPassword}")
  private String defaultPassword;
  
  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User loadedUser = delegate.loadUser(userRequest);
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    SocialOauthAttribute attributes = SocialOauthAttribute.of(registrationId, userNameAttributeName, loadedUser.getAttributes());
    MemberVo memberVo = attributes.toVo();
    MemberVo selVo = memberDao.selectByEmail(memberVo.getEmail());
    if (selVo == null) {
      memberDao.insert(memberVo);
      selVo = memberDao.selectByEmail(memberVo.getEmail());
    }
    return new SessionUser(selVo);
  }
}
