package spring.auth.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.MemberAuthenticationDao;
import spring.auth.api.vo.MemberAuthenticationVo;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  final MemberAuthenticationDao memberAuthenticationDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    MemberAuthenticationVo memberVo = memberAuthenticationDao.selectByEmail(email).orElseThrow(() -> new AppException(ERROR_CODE.A003));
    return new UserAuthentication(TOKEN.USER.MEMBER, memberVo);
  }
}
