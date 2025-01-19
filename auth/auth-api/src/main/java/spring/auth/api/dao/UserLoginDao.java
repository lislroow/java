package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.ManagerLoginVo;
import spring.auth.api.vo.MemberLoginVo;

@Mapper
public interface UserLoginDao {

  public Optional<MemberLoginVo> selectMemberByLoginId(String loginId);
  public Optional<ManagerLoginVo> selectManagerByLoginId(String loginId);
  
}
