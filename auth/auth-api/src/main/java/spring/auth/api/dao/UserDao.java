package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;

@Mapper
public interface UserDao {
  
  public Optional<MemberVo> selectMemberInfoById(String id);
  
  public Optional<ManagerVo> selectManagerInfoById(String id);
  
}
