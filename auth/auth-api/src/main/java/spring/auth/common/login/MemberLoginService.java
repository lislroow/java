package spring.auth.common.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  final UserLoginDao userLoginDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // `username` 은 client 에서 로그인 request 에 전달한 username 필드값
    // 로그인 이후, `UserAuthentication` 사용되는 username 과는 다름
    LoginVo.MemberLoginVo loginVo = userLoginDao.selectMemberByLoginId(username)
        .orElseThrow(() -> new AppException(ERROR.A003));
    return new UserAuthentication(TOKEN.USER_TYPE.MEMBER, loginVo);
  }
  
}
