package spring.auth.common.login;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPasswordEncoder implements PasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {
    /* for debug */ if (log.isDebugEnabled()) log.info("rawPassword: {}", rawPassword);
    return "";
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    /* for debug */ if (log.isDebugEnabled()) log.info("rawPassword: {}, encodedPassword: {}", rawPassword, encodedPassword);
    return true;
  }
  
}
