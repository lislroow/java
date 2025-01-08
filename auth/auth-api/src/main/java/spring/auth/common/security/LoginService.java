package spring.auth.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.MemberDao;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.MemberVo;
import spring.custom.common.vo.UserPrincipal;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  final MemberDao memberDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    MemberVo memberVo = memberDao.selectByEmail(email).orElseThrow(() -> new AppException(ERROR_CODE.A003));
    return new UserPrincipal(TOKEN.USER.MEMBER, memberVo.toMap());
  }
}
