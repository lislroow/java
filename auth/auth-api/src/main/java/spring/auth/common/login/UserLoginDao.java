package spring.auth.common.login;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.LoginVo;

@Mapper
public interface UserLoginDao {

  public Optional<LoginVo.ManagerLoginVo> selectManagerByLoginId(String loginId);
  
  public Optional<LoginVo.MemberLoginVo> selectMemberByLoginId(String loginId);
  
}
