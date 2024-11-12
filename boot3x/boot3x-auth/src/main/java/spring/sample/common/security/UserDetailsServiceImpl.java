package spring.sample.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.sample.common.dao.UserDao;
import spring.sample.common.vo.UserVo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserDao userDao;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    UserVo userVo = userDao.selectUserByEmail(email);
    return userVo;
  }
}
