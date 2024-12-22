package spring.sample.common.security;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginDao {
  
  public LoginVo selectManagerByEmail(@Param("email") String email);
  
}
