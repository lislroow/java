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
import spring.custom.common.exception.AppException;
import spring.custom.common.syscode.ERROR;
import spring.custom.common.syscode.ROLE;
import spring.custom.common.syscode.TOKEN;
import spring.custom.common.syscode.YN;

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
    
    String oauth2Id = attributes.getOauth2Id();
    Optional<LoginVo.MemberOAuth2Vo> oauth2LoginVo = userLoginDao.selectMemberOAuth2ByOAuth2Id(oauth2Id);
    
    if (oauth2LoginVo.isPresent()) {
      // select user
      return oauth2LoginVo.get().toOAuth2();
    } else {
      // registration oauth2 authentication
      LoginVo.MemberOAuth2AddVo memberOAuth2AddVo = attributes.toMemberOAuth2RegisterVo();
      
      Optional<String> optId = userLoginDao.selectIdByEmail(memberOAuth2AddVo.getEmail());
      if (optId.isPresent()) {
        memberOAuth2AddVo.setId(optId.get());
      } else {
        String id = userMngDao.selectNextId(TOKEN.USER.MEMBER.idprefix());
        memberOAuth2AddVo.setId(id);
        LoginVo.MemberAddVo memberAddVo = LoginVo.MemberAddVo.builder()
            .id(memberOAuth2AddVo.getId())
            .loginId(memberOAuth2AddVo.getEmail())
            .roles(ROLE.MEMBER.name())
            .nickname(memberOAuth2AddVo.getNickname())
            .enableYn(YN.Y)
            .lockedYn(YN.N)
            .build();
        userLoginDao.insertMember(memberAddVo);
      }
      userLoginDao.insertMemberOAuth2(memberOAuth2AddVo);
      
      // select user
      LoginVo.MemberOAuth2Vo loginVo = userLoginDao.selectMemberOAuth2ByOAuth2Id(oauth2Id)
          .orElseThrow(() -> new AppException(ERROR.A003));
      
      // return 'OAuth2User'
      return loginVo.toOAuth2();
    }
  }
  
}
