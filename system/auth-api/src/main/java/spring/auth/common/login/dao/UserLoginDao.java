package spring.auth.common.login.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.common.login.vo.LoginVo;

@Mapper
public interface UserLoginDao {

  Optional<LoginVo.ManagerVo> selectManagerByLoginId(String loginId);
  
  Optional<LoginVo.MemberVo> selectMemberByLoginId(String loginId);
  
  Optional<LoginVo.MemberOAuth2Vo> selectMemberOAuth2ByOAuth2Id(String oauth2Id);
  
  Optional<String> selectIdByEmail(String email);
  
  int insertMemberOAuth2(LoginVo.MemberOAuth2AddVo memberOAuth2AddVo);
  
  int insertMember(LoginVo.MemberAddVo memberAddVo);
}
