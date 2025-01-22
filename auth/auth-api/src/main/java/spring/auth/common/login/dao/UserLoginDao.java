package spring.auth.common.login.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.common.login.vo.LoginVo;

@Mapper
public interface UserLoginDao {

  Optional<LoginVo.ManagerVo> selectManagerByLoginId(String loginId);
  
  Optional<LoginVo.MemberVo> selectMemberByLoginId(String loginId);
  
  Optional<String> selectMemberIdByOauth2Id(String oauth2Id);
  
  Optional<LoginVo.MemberVo> selectMemberById(String loginId);
  
  int insertMemberOauth(LoginVo.MemberSnsVo memberSnsVo);
  
  int insertMember(LoginVo.MemberRegisterVo memberRegisterVo);
}
