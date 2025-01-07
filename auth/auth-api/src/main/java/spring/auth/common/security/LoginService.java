package spring.auth.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import spring.custom.api.dao.MemberDao;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.exception.AppException;
import spring.custom.common.vo.AuthPrincipal;
import spring.custom.common.vo.MemberVo;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

  //Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  
  //final UserRepository userRepository;
  //final ModelMapper model;
  final MemberDao memberDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    //MDC.put("userId", email);
    //ecslog.info("");
    MemberVo memberVo = memberDao.selectByEmail(email).orElseThrow(() -> new AppException(ERROR_CODE.A003));
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    memberVo.setIp(request.getRemoteAddr());
    memberVo.setUserAgent(request.getHeader(Constant.HTTP_HEADER.USER_AGENT));
    return new AuthPrincipal(TOKEN.USER.MEMBER, memberVo);
  }
}
