package spring.auth.common.security;

import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.MemberDao;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.enumcode.Role;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.AuthPrincipal;
import spring.custom.common.vo.MemberVo;

@Service
@RequiredArgsConstructor
public class SocialOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  
  final MemberDao memberDao;
  //final UserRepository userRepository;
  final ModelMapper model;
  
  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User loadedUser = delegate.loadUser(userRequest);
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    SocialOauthAttribute attributes = SocialOauthAttribute.of(registrationId, userNameAttributeName, loadedUser.getAttributes());
    MemberVo vo = attributes.toVo();
    MemberVo memberVo = memberDao.selectByEmail(vo.getEmail()).orElseGet(() -> {
      memberDao.insert(vo);
      return memberDao.selectByEmail(vo.getEmail()).orElseThrow(() -> new AppException(ERROR_CODE.AL02));
    });
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    memberVo.setIp(request.getRemoteAddr());
    memberVo.setUserAgent(request.getHeader(Constant.HTTP_HEADER.USER_AGENT));
    memberVo.setRole(Role.ROLE_USER.name());
    return new AuthPrincipal(TOKEN.USER.MEMBER, memberVo);
  }
}
