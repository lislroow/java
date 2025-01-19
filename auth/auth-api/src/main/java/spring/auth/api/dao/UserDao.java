package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;

@Mapper
public interface UserDao {
  
  public Optional<MemberVo> selectMemberById(String id);
  
  public Optional<ManagerVo> selectManagerById(String id);
  
}
