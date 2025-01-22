package spring.auth.common.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.auth.common.login.dao.UserLoginDao;
import spring.auth.common.login.vo.LoginVo;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.Principal;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  final UserLoginDao userLoginDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // select user
    LoginVo.MemberVo loginVo = userLoginDao.selectMemberByLoginId(username)
        .orElseThrow(() -> new AppException(ERROR.A003));
    
    // return 'UserDetails'
    Principal principal = loginVo.toPrincipal();
    String roles = loginVo.getRoles();
    String password = loginVo.getLoginPwd();
    
    return new UserAuthentication(principal, roles, password);
  }
  
}
