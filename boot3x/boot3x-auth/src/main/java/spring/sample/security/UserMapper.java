package spring.sample.security;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  
  public User selectUserByEmail(String email);
  
}
