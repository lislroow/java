package spring.auth.api.dao;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.common.vo.MemberVo;

@Mapper
public interface MngMemberDao {
  
  public int insert(MemberVo param);
  
}
