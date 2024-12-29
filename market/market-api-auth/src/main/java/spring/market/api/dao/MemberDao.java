package spring.market.api.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.market.common.vo.MemberVo;

@Mapper
public interface MemberDao {
  
  public int insert(MemberVo param);
  public MemberVo selectByEmail(@Param("email") String email);
  
}
