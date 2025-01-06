package spring.custom.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.custom.common.vo.MemberVo;

@Mapper
public interface MemberDao {
  
  public Optional<MemberVo> selectByEmail(@Param("email") String email);
  
}
