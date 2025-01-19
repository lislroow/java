package spring.auth.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.ManagerAuthDao;
import spring.auth.api.dao.MemberAuthDao;
import spring.auth.api.vo.ManagerAuthVo;
import spring.auth.api.vo.MemberAuthVo;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  final MemberAuthDao memberAuthDao;
  final ManagerAuthDao managerAuthDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // `username` 은 client 에서 로그인 request 에 전달한 username 필드값
    // 로그인 이후, `UserAuthentication` 사용되는 username 과는 다름
    MemberAuthVo authVo = memberAuthDao.selectByLoginId(username)
        .orElseThrow(() -> new AppException(ERROR.A003));
    if (authVo.getId().startsWith("1")) {
      ManagerAuthVo managerAuthVo = managerAuthDao.selectByLoginId(username)
          .orElseThrow(() -> new AppException(ERROR.A003));
      return new UserAuthentication(TOKEN.USER.MANAGER, managerAuthVo);
    } else {
      return new UserAuthentication(TOKEN.USER.MEMBER, authVo);
    }
  }
  
}
