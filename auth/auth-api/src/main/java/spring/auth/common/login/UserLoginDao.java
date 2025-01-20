package spring.auth.common.login;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginDao {

  Optional<LoginVo.ManagerLoginVo> selectManagerByLoginId(String loginId);
  
  Optional<LoginVo.MemberLoginVo> selectMemberByLoginId(String loginId);
  
  Optional<String> selectMemberIdByOauth2Id(String oauth2Id);
  
  Optional<LoginVo.MemberLoginVo> selectMemberById(String loginId);
  
  int insertMemberOauth(LoginVo.MemberSnsVo memberSnsVo);
  
  int insertMember(LoginVo.MemberRegisterVo memberRegisterVo);
}
