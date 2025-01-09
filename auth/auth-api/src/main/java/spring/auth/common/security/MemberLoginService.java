package spring.auth.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.MemberAuthDao;
import spring.auth.api.vo.MemberAuthVo;
import spring.custom.common.enumcode.Error;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  final MemberAuthDao memberAuthDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    MemberAuthVo memberVo = memberAuthDao.selectByEmail(email)
        .orElseThrow(() -> new AppException(Error.A003));
    return new UserAuthentication(TOKEN.USER.MEMBER, memberVo);
  }
  
}
