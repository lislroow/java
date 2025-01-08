package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.auth.api.vo.MemberAuthenticationVo;

@Mapper
public interface MemberAuthenticationDao {
  
  public Optional<MemberAuthenticationVo> selectByEmail(@Param("email") String email);
  
  public int insertOAuth2User(MemberAuthenticationVo param);
  
}
