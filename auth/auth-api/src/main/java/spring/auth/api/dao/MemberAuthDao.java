package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.auth.api.vo.MemberAuthVo;

@Mapper
public interface MemberAuthDao {
  
  public Optional<MemberAuthVo> selectByEmail(@Param("email") String email);
  
  public int insert(MemberAuthVo param);
  
}
