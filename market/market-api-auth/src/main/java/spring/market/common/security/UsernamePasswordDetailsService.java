package spring.market.common.security;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.market.api.dao.UserDao;
import spring.market.api.repository.UserRepository;
import spring.market.common.vo.SessionUser;
import spring.market.common.vo.User;

@Service
@RequiredArgsConstructor
public class UsernamePasswordDetailsService implements UserDetailsService {

  Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  
  final UserRepository userRepository;
  final UserDao userDao;
  final ModelMapper model;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    MDC.put("userId", email);
    ecslog.info("");
    User user = userDao.selectUserByEmail(email);
    return new SessionUser(user);
  }
}
