package spring.sample.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.common.vo.UserVo;

@Mapper
public interface UserDao {
  
  public UserVo selectUserByEmail(@Param("email") String email);
  
}
