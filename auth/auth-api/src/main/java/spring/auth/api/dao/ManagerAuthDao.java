package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.ManagerAuthVo;

@Mapper
public interface ManagerAuthDao {
  
  public Optional<ManagerAuthVo> selectByLoginId(String loginId);
  
  public Optional<ManagerAuthVo> selectById(String id);
  
}
