package spring.custom.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.custom.common.vo.ManagerVo;
import spring.custom.common.vo.MemberVo;

@Mapper
public interface UserInfoDao {
  
  public Optional<MemberVo> selectMemberByEmail(@Param("email") String email);
  
  public Optional<ManagerVo> selectManagerByMgrId(@Param("mgrId") String mgrId);
  
}
