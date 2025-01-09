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
import spring.custom.common.enumcode.Error;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  final MemberAuthDao memberAuthDao;
  final ManagerAuthDao managerAuthDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    MemberAuthVo authVo = memberAuthDao.selectByEmail(username)
        .orElseThrow(() -> new AppException(Error.A003));
    if (authVo.getMemberYn() == YN.Y) {
      return new UserAuthentication(TOKEN.USER.MEMBER, authVo);
    } else {
      ManagerAuthVo managerAuthVo = managerAuthDao.selectByMgrId(username)
          .orElseThrow(() -> new AppException(Error.A003));
      return new UserAuthentication(TOKEN.USER.MANAGER, managerAuthVo);
    }
  }
  
}
