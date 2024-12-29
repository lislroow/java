package spring.market.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.market.api.dao.MemberDao;
import spring.market.common.vo.MemberVo;
import spring.market.common.vo.SessionUser;

@Service
@RequiredArgsConstructor
public class UsernamePasswordDetailsService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  
  //final UserRepository userRepository;
  //final ModelMapper model;
  final MemberDao memberDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    //MDC.put("userId", email);
    //ecslog.info("");
    MemberVo memberVo = memberDao.selectByEmail(email);
    return new SessionUser(memberVo);
  }
}
