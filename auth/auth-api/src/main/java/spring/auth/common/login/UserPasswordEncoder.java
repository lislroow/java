package spring.auth.common.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPasswordEncoder implements PasswordEncoder {

  final BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
  
  @Override
  public String encode(CharSequence rawPassword) {
    /* for debug */ if (log.isInfoEnabled()) log.info("rawPassword: {}", rawPassword);
    return bcryptPasswordEncoder.encode(rawPassword);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    /* for debug */ if (log.isInfoEnabled()) log.info("rawPassword: {}, encodedPassword: {}", rawPassword, encodedPassword);
    // $2a$10$H2cA6hop3D3ffp6SlcOowOP4TLKURMO8yYvHdBogshlqCqNnk/nV2
    //return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
    return true;
  }
  
}
