package spring.auth.common.login.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.common.vo.ManagerPrincipal;
import spring.custom.common.vo.MemberPrincipal;

@Mapper
public interface UserDao {
  
  public Optional<MemberPrincipal> selectMemberInfoById(String id);
  
  public Optional<ManagerPrincipal> selectManagerInfoById(String id);
  
}
